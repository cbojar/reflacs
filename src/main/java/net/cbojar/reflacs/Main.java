package net.cbojar.reflacs;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;

import net.cbojar.reflacs.configuration.Configuration;
import net.cbojar.reflacs.ffmpeg.FFMPEG;
import net.cbojar.reflacs.files.FilesCollector;
import net.cbojar.reflacs.files.PathKeyMapper;
import net.cbojar.reflacs.formats.Format;
import net.cbojar.reflacs.formats.Formats;
import net.cbojar.reflacs.media.Collector;
import net.cbojar.reflacs.media.KeyMapper;
import net.cbojar.reflacs.media.MediaData;

public final class Main {
	public static void main(final String... args) throws IOException {
		final String source = args[0];
		final String destination = args[1];

		System.out.printf("Source: %s, Destination: %s%n", source, destination);

		final Configuration configuration = Configuration.load(source, destination);

		final Collector<Path> collector = FilesCollector.create(configuration);
		final Format format = Formats.withOptions(configuration.options());
		final KeyMapper<Path> keyMapper = PathKeyMapper.create(configuration.destination(), format);
		final FFMPEG converter = FFMPEG.of(format);

		for (final MediaData<Path> flac : collector.collect()) {
			writeToDestination(converter.convert(flac, keyMapper));
		}
	}

	private static void writeToDestination(final MediaData<Path> converted) {
		System.out.println(converted);

		try {
			Files.createDirectories(converted.key().getParent());
			Files.write(converted.key(), converted.bytes());
		} catch (final IOException ex) {
			throw new UncheckedIOException(ex);
		}
	}
}
