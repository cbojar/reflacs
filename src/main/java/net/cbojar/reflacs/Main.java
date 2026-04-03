package net.cbojar.reflacs;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import net.cbojar.reflacs.configuration.Configuration;
import net.cbojar.reflacs.ffmpeg.FFMPEG;
import net.cbojar.reflacs.files.FilesCollector;
import net.cbojar.reflacs.files.PathKeyMapper;
import net.cbojar.reflacs.formats.Format;
import net.cbojar.reflacs.formats.Formats;
import net.cbojar.reflacs.media.Collector;
import net.cbojar.reflacs.media.Media;

public final class Main {
	public static void main(final String... args) throws IOException {
		final String source = args[0];
		final String destination = args[1];

		System.out.printf("Source: %s, Destination: %s%n", source, destination);

		final Configuration configuration = Configuration.load(source, destination);

		final Collector<Path> collector = FilesCollector.create(configuration);
		final Format format = Formats.withOptions(configuration.options());
		final PathKeyMapper keyMapper = PathKeyMapper.create(configuration.destination(), format.extension());
		final FFMPEG converter = FFMPEG.of(format);

		for (final Media<Path> flac : collector.collect()) {
			writeToDestination(Media.of(keyMapper.map(flac.key()), converter.convert(flac.data())));
		}
	}

	private static void writeToDestination(final Media<Path> converted) throws IOException {
		System.out.println(converted);

		Files.createDirectories(converted.key().getParent());
		try (OutputStream out = Files.newOutputStream(converted.key())) {
			converted.writeDataTo(out);
		}
	}
}
