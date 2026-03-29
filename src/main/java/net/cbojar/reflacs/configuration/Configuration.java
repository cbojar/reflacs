package net.cbojar.reflacs.configuration;

import java.nio.file.Path;
import java.util.Map;

public final class Configuration {
	private static final Map<String, String> OPTIONS = Map.of(
		"output-format", "ogg",
		"bitrate", "256K");

	private final Path source;
	private final Path destination;

	private Configuration(final Path source, final Path destination) {
		this.source = source;
		this.destination = destination;
	}

	public static Configuration load(final String source, final String destination) {
		return new Configuration(Path.of(source), Path.of(destination));
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

	public Map<String, String> options() {
		return OPTIONS;
	}

	public String get(final String name) {
		return OPTIONS.get(name);
	}
}
