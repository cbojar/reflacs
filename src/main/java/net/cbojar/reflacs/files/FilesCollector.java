package net.cbojar.reflacs.files;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

import net.cbojar.reflacs.configuration.Configuration;

public final class FilesCollector implements Collector<Path> {
	private final Configuration configuration;

	private FilesCollector(final Configuration configuration) {
		this.configuration = configuration;
	}

	public static Collector<Path> create(final Configuration source) {
		return new FilesCollector(source);
	}

	@Override
	public Iterable<Flac<Path>> collect() {
		try (Stream<Path> stream = Files.find(configuration.source(), 10, FilesCollector::isFlacFile)) {
			final List<Path> flacs = stream.toList();
			return () -> new FlacIterator(configuration.source(), flacs.iterator());
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

	private static class FlacIterator implements Iterator<Flac<Path>> {
		private final Path source;
		private final Iterator<Path> flacs;

		public FlacIterator(final Path source, final Iterator<Path> flacs) {
			this.source = source;
			this.flacs = flacs;
		}

		@Override
		public boolean hasNext() {
			return flacs.hasNext();
		}

		@Override
		public Flac<Path> next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}

			final Path path = flacs.next();
			final Path relativePath = source.relativize(path);
			return Flac.of(relativePath, bytesFor(path));
		}

		private static byte[] bytesFor(final Path path) {
			try {
				return Files.readAllBytes(path);
			} catch (final IOException ex) {
				throw new UncheckedIOException(ex);
			}
		}
	}
}
