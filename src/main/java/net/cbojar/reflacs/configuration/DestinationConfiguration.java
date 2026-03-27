package net.cbojar.reflacs.configuration;

public final class DestinationConfiguration {
	private final String destination;

	private DestinationConfiguration(final String destination) {
		this.destination = destination;
	}

	public static DestinationConfiguration load(final String destination) {
		return new DestinationConfiguration(destination);
	}

	@Override
	public String toString() {
		return destination;
	}
}
