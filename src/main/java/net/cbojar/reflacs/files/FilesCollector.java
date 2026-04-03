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

import net.cbojar.reflacs.media.Collector;
import net.cbojar.reflacs.media.Media;
import net.cbojar.reflacs.media.MediaData;

public final class FilesCollector implements Collector<Path> {
	private final Path source;

	private FilesCollector(final Path source) {
		this.source = source;
	}

	public static Collector<Path> create(final String source) {
		return create(Path.of(source));
	}

	public static Collector<Path> create(final Path source) {
		return new FilesCollector(source);
	}

	@Override
	public Iterable<Media<Path>> collect() {
		try (Stream<Path> stream = Files.find(source, 10, FilesCollector::isFlacFile)) {
			final List<Path> flacs = stream.toList();
			return () -> new FlacIterator(source, flacs.iterator());
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

	private static class FlacIterator implements Iterator<Media<Path>> {
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
		public Media<Path> next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}

			final Path path = flacs.next();
			return Media.of(toExternal(source, path), MediaData.of(bytesFor(path)));
		}

		private static Path toExternal(final Path source, final Path absolute) {
			final String relativePath = source.relativize(absolute).toString();
			return Path.of(relativePath.substring(0, relativePath.length() - 5));
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
