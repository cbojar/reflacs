package net.cbojar.reflacs.files;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.stream.Stream;

import net.cbojar.reflacs.configuration.SourceConfiguration;
import net.cbojar.reflacs.media.Collector;
import net.cbojar.reflacs.media.Flac;

public final class FilesCollector implements Collector {
	private final SourceConfiguration configuration;

	private FilesCollector(final SourceConfiguration configuration) {
		this.configuration = configuration;
	}

	public static Collector create(final SourceConfiguration configuration) {
		return new FilesCollector(configuration);
	}

	@Override
	public Iterable<Flac> collect() {
		try (Stream<Path> stream = Files.find(Paths.get(configuration.path()), 10, FilesCollector::isFlacFile)) {
			return stream
				.map(Path::toString)
				.map(Flac::of)
				.toList();
		} catch (final IOException ex) {
			throw new UncheckedIOException(ex);
		}
	}

	private static boolean isFlacFile(final Path path, final BasicFileAttributes attributes) {
		return attributes.isRegularFile() && fileSuffixOf(path.getFileName()).equalsIgnoreCase(".flac");
	}

	private static String fileSuffixOf(final Path path) {
		final String fileName = path.getFileName().toString();
		return fileName.substring(fileName.length() - 5);
	}
}
