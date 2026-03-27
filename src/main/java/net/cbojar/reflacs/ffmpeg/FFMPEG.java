package net.cbojar.reflacs.ffmpeg;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ProcessBuilder.Redirect;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import net.cbojar.reflacs.media.Converted;
import net.cbojar.reflacs.media.Converter;
import net.cbojar.reflacs.media.ConverterConfiguration;
import net.cbojar.reflacs.media.Flac;

public final class FFMPEG implements Converter {
	private final ConverterConfiguration configuration;

	private FFMPEG(final ConverterConfiguration configuration) {
		this.configuration = configuration;
	}

	public static Converter converter(final ConverterConfiguration configuration) {
		return new FFMPEG(configuration);
	}

	@Override
	public Converted convert(final Flac flac) throws IOException {
		final List<String> command = new ArrayList<>(Arrays.asList("ffmpeg", "-i", "-", "-f", configuration.outputFormat()));
		command.addAll(configuration.additionalOptions());
		command.add("-");

		final Process ffmpeg = new ProcessBuilder(command)
				.redirectError(Redirect.INHERIT)
				.start();

		final String commandLine = ffmpeg.info().commandLine().orElseGet(() -> String.join(" ", command));

		final ByteArrayOutputStream convertedBytes = new ByteArrayOutputStream();
		final List<Future<IOException>> futures = new ArrayList<>();
		final ExecutorService executor = Executors.newFixedThreadPool(2);
		futures.add(executor.submit(() -> sendInput(ffmpeg, flac)));
		futures.add(executor.submit(() -> receiveOutput(ffmpeg, convertedBytes)));
		executor.shutdown();

		try {
			final int exitCode = ffmpeg.waitFor();
			if (exitCode != 0) {
				throw new IOException(String.format("\"%s\" exited with non-zero code: %d",
						commandLine, Integer.valueOf(exitCode)));
			}
		} catch (final InterruptedException ex) {
			throw new IOException(String.format("\"%s\" interrupted during conversion", commandLine), ex);
		}

		final IOException ex = futures.stream()
				.map(FFMPEG::extractFuture)
				.filter(result -> result != null)
				.findFirst()
				.orElse(null);

		if (ex != null) {
			throw ex;
		}

		return Converted.of(rename(flac, configuration.outputFormat()), convertedBytes.toByteArray());
	}

	private static IOException sendInput(final Process ffmpeg, final Flac flac) {
		try (final OutputStream outputStream = ffmpeg.getOutputStream()) {
			outputStream.write(flac.bytes());
			return null;
		} catch (final IOException ex) {
			return ex;
		}
	}

	private static IOException receiveOutput(final Process ffmpeg, final OutputStream destination) {
		try (final InputStream inputStream = ffmpeg.getInputStream()) {
			destination.write(inputStream.readAllBytes());
			return null;
		} catch (final IOException ex) {
			return ex;
		}
	}

	private static IOException extractFuture(final Future<IOException> future) {
		try {
			return future.get();
		} catch (final InterruptedException | ExecutionException ex) {
			return new IOException(ex);
		}
	}

	private static String rename(final Flac flac, final String outputFormat) {
		return String.format("%s.%s", flac.name().substring(0, flac.name().length() - 5), outputFormat);
	}
}
