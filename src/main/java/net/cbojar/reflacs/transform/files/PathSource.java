package net.cbojar.reflacs.transform.files;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.stream.Stream;

import net.cbojar.reflacs.transform.Input;
import net.cbojar.reflacs.transform.Source;

public final class PathSource implements Source {
	private final Path source;

	private PathSource(final Path source) {
		this.source = source;
	}

	public static Source from(final Path source) {
		return new PathSource(source);
	}

	@Override
	public Iterable<Input> inputs() throws IOException {
		try (Stream<Path> stream = Files.find(source, 10, PathSource::isFlacFile)) {
			final List<Path> flacs = stream.toList();
			return () -> new PathIterator(source, flacs.iterator());
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
