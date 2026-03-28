package net.cbojar.reflacs;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;

import net.cbojar.reflacs.configuration.DestinationConfiguration;
import net.cbojar.reflacs.configuration.SourceConfiguration;
import net.cbojar.reflacs.ffmpeg.FFMPEG;
import net.cbojar.reflacs.ffmpeg.FFMPEGConfiguration;
import net.cbojar.reflacs.files.FilesCollector;
import net.cbojar.reflacs.media.Collector;
import net.cbojar.reflacs.media.Converted;
import net.cbojar.reflacs.media.Flac;

public final class Main {
	public static void main(final String... args) throws IOException {
		final String source = args[0];
		final String destination = args[1];

		System.out.printf("Source: %s, Destination: %s%n", source, destination);

		final SourceConfiguration sourceConfiguration = SourceConfiguration.load(source);
		final DestinationConfiguration destinationConfiguration = DestinationConfiguration.load(destination);

		final Collector collector = FilesCollector.create(sourceConfiguration);
		final FFMPEG converter = FFMPEG.of(new FFMPEGConfiguration());

		for (final Flac flac : collector.collect()) {
			writeToDestination(converter.convert(flac), source, destinationConfiguration.toString());
		}
	}

	private static void writeToDestination(final Converted converted, final String source, final String destination) {
		System.out.println(converted);

		try {
			Files.write(Path.of(converted.name().replace(source, destination)), converted.bytes());
		} catch (final IOException ex) {
			throw new UncheckedIOException(ex);
		}
	}
}
