package net.cbojar.reflacs.configuration;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

public final class Configuration {
	private final Path source;
	private final Path destination;
	private final Map<String, String> options;

	private Configuration(final Path source, final Path destination, final Map<String, String> options) {
		this.source = source;
		this.destination = destination;
		this.options = options;
	}

	public static Configuration load(final String source, final String destination) throws IOException {
		return load(Path.of(source), Path.of(destination));
	}

	public static Configuration load(final Path source, final Path destination) throws IOException {
		return new Configuration(source, destination, readDestination(destination));
	}

	private static Map<String, String> readDestination(final Path destination) throws IOException {
		final Path configurationFile = destination.resolve(".reflacs");

		if (!Files.exists(configurationFile)) {
			throw new IOException("Reflacs configuration file not found: " + configurationFile);
		}

		final Properties properties = new Properties();
		try (final BufferedReader configurationReader = Files.newBufferedReader(configurationFile)) {
			properties.load(configurationReader);
		}

		return propertiesToMap(properties);
	}

	private static Map<String, String> propertiesToMap(final Properties properties) {
		final Map<String, String> map = new LinkedHashMap<>();
		for (final String property : properties.stringPropertyNames()) {
			map.put(property, properties.getProperty(property));
		}

		return map;
	}

	public Path source() {
		return source;
	}

	public Path destination() {
		return destination;
	}

	public Path mapSourceToDestination(final Path path) {
		return destination.resolve(source.relativize(path));
	}

	public String get(final String name) {
		return options.get(name);
	}
}
