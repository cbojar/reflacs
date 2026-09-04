package net.cbojar.reflacs.transform.files;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

import net.cbojar.reflacs.formats.Format;
import net.cbojar.reflacs.formats.Formats;
import net.cbojar.reflacs.formats.Options;
import net.cbojar.reflacs.transform.Destination;
import net.cbojar.reflacs.transform.Output;

public final class PathDestination implements Destination {
	private final Path root;

	private PathDestination(final Path root) {
		this.root = root;
	}

	public static Destination to(final Path destination) {
		return new PathDestination(destination);
	}

	@Override
	public Format readFormat() throws IOException {
		final Path file = root.resolve(".reflacs");

		if (!Files.exists(file)) {
			throw new IOException("Reflacs configuration file not found: " + file);
		}

		final Properties properties = new Properties();
		try (final BufferedReader configurationReader = Files.newBufferedReader(file)) {
			properties.load(configurationReader);
		}

		return Formats.withOptions(Options.of(properties));
	}


	@Override
	public boolean exists(final Path path) {
		return Files.exists(root.resolve(path));
	}

	@Override
	public void write(final Output output) throws IOException {
		final Path destination = root.resolve(output.name());

		Files.createDirectories(destination.getParent());
		try (OutputStream out = Files.newOutputStream(destination)) {
			output.data().writeTo(out);
		}
	}
}
