package net.cbojar.reflacs.transform.files;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.function.Consumer;

public class InMemoryPathStore implements PathStore {
	private final Map<Path, byte[]> data = new HashMap<>();

	@Override
	public boolean exists(final Path path) {
		return data.containsKey(path);
	}

	public InMemoryPathStore write(final String path, final byte[] bytes) {
		return write(Path.of(path), bytes);
	}

	public InMemoryPathStore write(final Path path, final byte[] bytes) {
		data.put(path, bytes);
		return this;
	}

	@Override
	public List<Path> findFlacFiles(final Path start) throws IOException {
		return data.keySet().stream()
			.filter(path -> path.startsWith(start))
			.filter(path -> path.toString().endsWith(".flac"))
			.toList();
	}

	@Override
	public OutputStream outputStreamTo(final Path path) throws IOException {
		return new ByteArrayOutputStream() {
			@Override
			public void close() throws IOException {
				super.close();
				data.put(path, toByteArray());
			}
		};
	}

	public InMemoryPathStore writeProperties(final String file, final Consumer<Properties> setupProperties) {
		return writeProperties(Path.of(file), setupProperties);
	}

	public InMemoryPathStore writeProperties(final Path file, final Consumer<Properties> setupProperties) {
		final Properties properties = new Properties();
		setupProperties.accept(properties);
		return writeProperties(file, properties);
	}

	public InMemoryPathStore writeProperties(final String file, final Properties properties) {
		return writeProperties(Path.of(file), properties);
	}

	public InMemoryPathStore writeProperties(final Path file, final Properties properties) {
		final ByteArrayOutputStream out = new ByteArrayOutputStream();

		try {
			properties.store(out, "");
		} catch (final IOException ex) {
			throw new IllegalStateException("Unable to store properties", ex);
		}

		data.put(file, out.toByteArray());

		return this;
	}

	@Override
	public Properties readProperties(final Path file) throws IOException {
		final byte[] bytes = get(file).orElseThrow(() -> new IOException("No such properties file:" + file));

		final Properties properties = new Properties();
		properties.load(new ByteArrayInputStream(bytes));
		return properties;
	}

	public byte[] readAllBytes(final String path) throws IOException {
		return readAllBytes(Path.of(path));
	}

	@Override
	public byte[] readAllBytes(final Path path) throws IOException {
		return get(path).orElseThrow(() -> new IOException("No such file: " + path));
	}

	private Optional<byte[]> get(final Path path) {
		return Optional.ofNullable(data.get(path));
	}
}
