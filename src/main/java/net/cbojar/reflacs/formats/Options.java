package net.cbojar.reflacs.formats;

import java.util.Optional;
import java.util.Properties;

public final class Options {
	private final String outputFormat;
	private final String quality;
	private final String bitrate;

	private Options(final String outputFormat, final String quality, final String bitrate) {
		this.outputFormat = outputFormat;
		this.quality = quality;
		this.bitrate = bitrate;
	}

	public static Options of(final Properties properties) {
		final String outputFormat = properties.getProperty("output-format");
		final String quality = properties.getProperty("quality");
		final String bitrate = properties.getProperty("bitrate");
		return new Options(outputFormat, quality, bitrate);
	}

	public Optional<String> outputFormat() {
		return Optional.ofNullable(outputFormat);
	}

	public Optional<String> quality() {
		return Optional.ofNullable(quality);
	}

	public Optional<String> bitrate() {
		return Optional.ofNullable(bitrate);
	}
}
