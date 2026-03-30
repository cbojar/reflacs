package net.cbojar.reflacs.formats;

import java.util.Optional;

import net.cbojar.reflacs.configuration.Options;

final class Opus implements Format {
	private final Options options;

	Opus(final Options options) {
		this.options = options;
	}

	@Override
	public String format() {
		return "opus";
	}

	@Override
	public String extension() {
		return "opus";
	}

	@Override
	public Optional<String> codec() {
		return Optional.of("libopus");
	}

	@Override
	public Optional<String> quality() {
		return Optional.empty();
	}

	@Override
	public Optional<String> bitrate() {
		return options.get("bitrate");
	}
}
