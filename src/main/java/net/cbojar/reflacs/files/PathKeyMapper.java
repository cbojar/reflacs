package net.cbojar.reflacs.files;

import java.nio.file.Path;

public final class PathKeyMapper {
	private final Path destination;
	private final String extension;

	private PathKeyMapper(final Path destination, final String extension) {
		this.destination = destination;
		this.extension = extension;
	}

	public static PathKeyMapper create(final Path destination, final String extension) {
		return new PathKeyMapper(destination, extension);
	}

	public Path map(final Path key) {
		return destination.resolve(String.format("%s.%s", key, extension));
	}
}
