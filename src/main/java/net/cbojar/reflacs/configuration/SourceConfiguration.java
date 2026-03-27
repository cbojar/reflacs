package net.cbojar.reflacs.configuration;

public final class SourceConfiguration {
	private final String path;

	private SourceConfiguration(final String path) {
		this.path = path;
	}

	public static SourceConfiguration load(final String source) {
		return new SourceConfiguration(source);
	}

	public String path() {
		return path;
	}
}
