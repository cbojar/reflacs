package net.cbojar.reflacs.ffmpeg;

import java.util.Arrays;
import java.util.List;

import net.cbojar.reflacs.configuration.Configuration;

public final class FFMPEGConfiguration {
	private final String outputFormat;
	private final String bitrate;

	private FFMPEGConfiguration(final String outputFormat, final String bitrate) {
		this.outputFormat = outputFormat;
		this.bitrate = bitrate;
	}

	public static FFMPEGConfiguration loadFrom(final Configuration configuration) {
		final String outputFormat = configuration.get("output-format");
		final String bitrate = configuration.get("bitrate");

		return new FFMPEGConfiguration(outputFormat, bitrate);
	}

	public String outputFormat() {
		return outputFormat;
	}

	public List<String> additionalOptions() {
		return Arrays.asList("-b:a", bitrate);
	}
}
