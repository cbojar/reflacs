package net.cbojar.reflacs.files;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.stream.Stream;

import net.cbojar.reflacs.storage.Collector;
import net.cbojar.reflacs.storage.Source;

public final class FilesCollector implements Collector {
	private final Path source;

	private FilesCollector(final Path source) {
		this.source = source;
	}

	public static Collector from(final Path source) {
		return new FilesCollector(source);
	}

	@Override
	public Iterable<Source> collect() throws IOException {
		try (Stream<Path> stream = Files.find(source, 10, FilesCollector::isFlacFile)) {
			final List<Path> flacs = stream.toList();
			return () -> new FilesIterator(source, flacs.iterator());
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
