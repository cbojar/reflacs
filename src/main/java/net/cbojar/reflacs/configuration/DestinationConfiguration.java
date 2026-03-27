package net.cbojar.reflacs.configuration;

import java.util.Arrays;
import java.util.List;

import net.cbojar.reflacs.media.ConverterConfiguration;

public final class DestinationConfiguration implements ConverterConfiguration {
	private final String destination;

	private DestinationConfiguration(final String destination) {
		this.destination = destination;
	}

	public static DestinationConfiguration load(final String destination) {
		return new DestinationConfiguration(destination);
	}

	@Override
	public String outputFormat() {
		return "ogg";
	}

	@Override
	public List<String> additionalOptions() {
		return Arrays.asList("-b:a", "256K");
	}

	@Override
	public String toString() {
		return destination;
	}
}
