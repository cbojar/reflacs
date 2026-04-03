package net.cbojar.reflacs.files;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.NoSuchElementException;

import net.cbojar.reflacs.media.Media;

class FilesIterator implements Iterator<Media<Path>> {
	private final Path source;
	private final Iterator<Path> flacs;

	public FilesIterator(final Path source, final Iterator<Path> flacs) {
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
		return Media.of(toExternal(source, path), bytesFor(path));
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
