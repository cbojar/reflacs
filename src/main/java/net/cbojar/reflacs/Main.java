package net.cbojar.reflacs;

import java.util.Collections;

import net.cbojar.reflacs.configuration.DestinationConfiguration;
import net.cbojar.reflacs.configuration.SourceConfiguration;
import net.cbojar.reflacs.ffmpeg.FFMPEG;
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

		final Converter converter = FFMPEG.converter(destinationConfiguration);

		for (final Flac flac : showUI(sourceConfiguration)) {
			writeToDestination(converter.convert(flac));
		}
	}

	private static Iterable<Flac> showUI(final SourceConfiguration sourceConfiguration) {
		return Collections.emptyList();
	}

	private static void writeToDestination(final Converted converted) {
	}
}
