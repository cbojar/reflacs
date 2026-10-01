package net.cbojar.reflacs.transform.files;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.Properties;
import java.util.stream.Stream;

import net.cbojar.reflacs.transform.PathStore;

public final class FilesPathStore implements PathStore {
	private static final FilesPathStore INSTANCE = new FilesPathStore();

	private FilesPathStore() {
		// Prevent external instantiation
	}

	public static FilesPathStore instance() {
		return INSTANCE;
	}

	@Override
	public boolean exists(final Path path) {
		return Files.exists(path);
	}

	@Override
	public List<Path> findFlacFiles(final Path start) throws IOException {
		try (Stream<Path> stream = Files.find(start, 10, FilesPathStore::isFlacFile)) {
			return stream.toList();
		}
	}

	private static boolean isFlacFile(final Path path, final BasicFileAttributes attributes) {
		return attributes.isRegularFile() && path.toString().toLowerCase().endsWith(".flac");
	}

	@Override
	public OutputStream outputStreamTo(final Path path) throws IOException {
		Files.createDirectories(path.getParent());
		return Files.newOutputStream(path);
	}

	@Override
	public Properties readProperties(final Path file) throws IOException {
		final Properties properties = new Properties();

		try (final BufferedReader configurationReader = Files.newBufferedReader(file)) {
			properties.load(configurationReader);
		}

		return properties;
	}

	@Override
	public byte[] readAllBytes(final Path path) throws IOException {
		return Files.readAllBytes(path);
	}
}
