package net.cbojar.reflacs.configuration;

import java.util.Optional;
import java.util.Properties;

public final class Options {
	private final Properties options;

	private Options(final Properties options) {
		this.options = options;
	}

	static Options of(final Properties properties) {
		return new Options(properties);
	}

	public Optional<String> get(final String key) {
		return Optional.ofNullable(options.getProperty(key));
	}
}
