package net.cbojar.reflacs.ffmpeg;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import net.cbojar.reflacs.media.Converted;
import net.cbojar.reflacs.media.Flac;

public final class FFMPEG {
	private final FFMPEGConfiguration configuration;

	private FFMPEG(final FFMPEGConfiguration configuration) {
		this.configuration = configuration;
	}

	public static FFMPEG of(final FFMPEGConfiguration configuration) {
		return new FFMPEG(configuration);
	}

	public Converted convert(final Flac flac) throws IOException {
		final ByteArrayOutputStream convertedBytes = new ByteArrayOutputStream();

		Run.start(Command.build(configuration)).withBlock(pipes -> {
			pipes.pipeIn(outputStream -> outputStream.write(flac.bytes()));
			pipes.pipeOut(inputStream -> convertedBytes.write(inputStream.readAllBytes()));
		});

		return Converted.of(rename(flac, configuration.outputFormat()), convertedBytes.toByteArray());
	}

	private static String rename(final Flac flac, final String outputFormat) {
		return String.format("%s.%s", flac.name().substring(0, flac.name().length() - 5), outputFormat);
	}
}
