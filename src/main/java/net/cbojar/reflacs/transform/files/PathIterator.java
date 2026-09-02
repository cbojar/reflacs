package net.cbojar.reflacs.transform.files;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.NoSuchElementException;

import net.cbojar.reflacs.transform.Input;

class PathIterator implements Iterator<Input> {
	private final Path root;
	private final Iterator<Path> flacs;

	public PathIterator(final Path root, final Iterator<Path> flacs) {
		this.root = root;
		this.flacs = flacs;
	}

	@Override
	public boolean hasNext() {
		return flacs.hasNext();
	}

	@Override
	public Input next() {
		if (!hasNext()) {
			throw new NoSuchElementException();
		}

		final Path path = flacs.next();
		return Input.of(toExternal(root, path), bytesFor(path));
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
