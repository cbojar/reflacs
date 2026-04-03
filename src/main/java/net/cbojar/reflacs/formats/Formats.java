package net.cbojar.reflacs.formats;

import java.io.IOException;

public final class Formats {
	public static Format withOptions(final Options options) throws IOException {
		final String formatName = options.get("output-format")
			.map(String::toLowerCase)
			.orElseThrow(() -> new IOException("Output format not specified in configuration file"));

		switch (formatName) {
			case "aac":
				return aac(options);
			case "mp3":
				return mp3(options);
			case "ogg":
				return ogg(options);
			case "opus":
				return opus(options);
			default:
				throw new IOException(String.format("Unknown format: \"%s\"", formatName));
		}
	}

	public static Format aac(final Options options) {
		return new AAC(options);
	}

	public static Format mp3(final Options options) {
		return new MP3(options);
	}

	public static Format ogg(final Options options) {
		return new Ogg(options);
	}

	public static Format opus(final Options options) {
		return new Opus(options);
	}
}
