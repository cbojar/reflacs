package net.cbojar.reflacs;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;

import net.cbojar.reflacs.configuration.Configuration;
import net.cbojar.reflacs.ffmpeg.FFMPEG;
import net.cbojar.reflacs.ffmpeg.FFMPEGConfiguration;
import net.cbojar.reflacs.files.Collector;
import net.cbojar.reflacs.files.Converted;
import net.cbojar.reflacs.files.FilesCollector;
import net.cbojar.reflacs.files.Flac;
import net.cbojar.reflacs.media.MediaData;

public final class Main {
	public static void main(final String... args) throws IOException {
		final String source = args[0];
		final String destination = args[1];

		System.out.printf("Source: %s, Destination: %s%n", source, destination);

		final Configuration configuration = Configuration.load(source, destination);

		final Collector collector = FilesCollector.create(configuration);
		final FFMPEGConfiguration ffmpegConfiguration = FFMPEGConfiguration.loadFrom(configuration);
		final FFMPEG converter = FFMPEG.of(ffmpegConfiguration);

		for (final Flac flac : collector.collect()) {
			final MediaData convertedData = converter.convert(flac.data());
			final Path convertedPath = configuration.mapSourceToDestination(flac.path());
			final Converted converted = Converted.of(convertedPath, convertedData);
			writeToDestination(converted);
		}
	}

	private static void writeToDestination(final Converted converted) {
		System.out.println(converted);

		try {
			Files.createDirectories(converted.path().getParent());
			Files.write(converted.path(), converted.bytes());
		} catch (final IOException ex) {
			throw new UncheckedIOException(ex);
		}
	}
}
