package net.cbojar.reflacs;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.stream.Stream;

import net.cbojar.reflacs.configuration.DestinationConfiguration;
import net.cbojar.reflacs.configuration.SourceConfiguration;
import net.cbojar.reflacs.ffmpeg.FFMPEG;
import net.cbojar.reflacs.media.Converted;
import net.cbojar.reflacs.media.Converter;
import net.cbojar.reflacs.media.Flac;

public final class Main {
	private static final PathMatcher FLAC_MATCHER = FileSystems.getDefault().getPathMatcher("glob:*.flac");

	public static void main(final String... args) {
		final String source = args[0];
		final String destination = args[1];

		System.out.printf("Source: %s, Destination: %s%n", source, destination);

		final SourceConfiguration sourceConfiguration = SourceConfiguration.load(source);
		final DestinationConfiguration destinationConfiguration = DestinationConfiguration.load(destination);

		final Converter converter = FFMPEG.converter(destinationConfiguration);

		for (final Flac flac : collectFlacs(sourceConfiguration)) {
			writeToDestination(converter.convert(flac));
		}
	}

	private static Iterable<Flac> collectFlacs(final SourceConfiguration configuration) {
		try (Stream<Path> stream = Files.find(Paths.get(configuration.path()), 10, Main::isFlacFile)) {
			return stream
				.map(Path::toString)
				.map(Flac::of)
				.toList();
		} catch (final IOException ex) {
			throw new UncheckedIOException(ex);
		}
	}

	private static boolean isFlacFile(final Path path, final BasicFileAttributes attributes) {
		return attributes.isRegularFile() && FLAC_MATCHER.matches(path.getFileName());
	}

	private static void writeToDestination(final Converted converted) {
		System.out.println(converted);
	}
}
