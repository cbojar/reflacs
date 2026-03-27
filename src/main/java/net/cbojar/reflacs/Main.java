package net.cbojar.reflacs;

import net.cbojar.reflacs.configuration.DestinationConfiguration;
import net.cbojar.reflacs.configuration.SourceConfiguration;
import net.cbojar.reflacs.ffmpeg.FFMPEG;
import net.cbojar.reflacs.files.FilesCollector;
import net.cbojar.reflacs.media.Collector;
import net.cbojar.reflacs.media.Converted;
import net.cbojar.reflacs.media.Converter;
import net.cbojar.reflacs.media.Flac;

public final class Main {
	public static void main(final String... args) {
		final String source = args[0];
		final String destination = args[1];

		System.out.printf("Source: %s, Destination: %s%n", source, destination);

		final SourceConfiguration sourceConfiguration = SourceConfiguration.load(source);
		final DestinationConfiguration destinationConfiguration = DestinationConfiguration.load(destination);

		final Collector collector = FilesCollector.create(sourceConfiguration);
		final Converter converter = FFMPEG.converter(destinationConfiguration);

		for (final Flac flac : collector.collect()) {
			writeToDestination(converter.convert(flac));
		}
	}

	private static void writeToDestination(final Converted converted) {
		System.out.println(converted);
	}
}
