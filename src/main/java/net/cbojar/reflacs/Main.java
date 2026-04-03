package net.cbojar.reflacs;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;

import net.cbojar.reflacs.configuration.Configuration;
import net.cbojar.reflacs.ffmpeg.FFMPEG;
import net.cbojar.reflacs.files.Collector;
import net.cbojar.reflacs.files.Converted;
import net.cbojar.reflacs.files.FilesCollector;
import net.cbojar.reflacs.files.Flac;
import net.cbojar.reflacs.formats.Format;
import net.cbojar.reflacs.formats.Formats;
import net.cbojar.reflacs.media.MediaData;

public final class Main {
	public static void main(final String... args) throws IOException {
		final String source = args[0];
		final String destination = args[1];

		System.out.printf("Source: %s, Destination: %s%n", source, destination);

		final Configuration configuration = Configuration.load(source, destination);

		final Collector<Path> collector = FilesCollector.create(configuration);
		final Format format = Formats.withOptions(configuration.options());
		final FFMPEG converter = FFMPEG.of(format);

		for (final Flac<Path> flac : collector.collect()) {
			final MediaData<Path> convertedData = converter.convert(flac.data());
			final Path convertedPath = configuration.mapToDestination(flac.key(), format.extension());
			final Converted<Path> converted = Converted.of(convertedPath, convertedData);
			writeToDestination(converted);
		}
	}

	private static void writeToDestination(final Converted<Path> converted) {
		System.out.println(converted);

		try {
			Files.createDirectories(converted.path().getParent());
			Files.write(converted.path(), converted.bytes());
		} catch (final IOException ex) {
			throw new UncheckedIOException(ex);
		}
	}
}
