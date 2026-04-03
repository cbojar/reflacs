package net.cbojar.reflacs.configuration;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public final class Configuration {
	private final Path destination;
	private final Options options;

	private Configuration(final Path destination, final Options options) {
		this.destination = destination;
		this.options = options;
	}

	public static Configuration load(final String destination) throws IOException {
		return load(Path.of(destination));
	}

	public static Configuration load(final Path destination) throws IOException {
		return new Configuration(destination, readDestination(destination));
	}

	private static Options readDestination(final Path destination) throws IOException {
		final Path configurationFile = destination.resolve(".reflacs");

		if (!Files.exists(configurationFile)) {
			throw new IOException("Reflacs configuration file not found: " + configurationFile);
		}

		final Properties properties = new Properties();
		try (final BufferedReader configurationReader = Files.newBufferedReader(configurationFile)) {
			properties.load(configurationReader);
		}

		return Options.of(properties);
	}

	public Path destination() {
		return destination;
	}

	public Options options() {
		return options;
	}
}
