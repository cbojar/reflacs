package net.cbojar.reflacs.files;

import java.nio.file.Path;

import net.cbojar.reflacs.formats.Format;
import net.cbojar.reflacs.media.KeyMapper;

public final class PathKeyMapper implements KeyMapper<Path> {
	private final Path destination;
	private final Format format;

	private PathKeyMapper(final Path destination, final Format format) {
		this.destination = destination;
		this.format = format;
	}

	public static KeyMapper<Path> create(final Path destination, final Format format) {
		return new PathKeyMapper(destination, format);
	}

	@Override
	public Path map(final Path key) {
		final Path withSuffix = Path.of(String.format("%s.%s", key, format.extension()));
		return destination.resolve(withSuffix);
	}
}
