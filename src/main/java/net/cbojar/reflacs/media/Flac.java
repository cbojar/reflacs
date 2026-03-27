package net.cbojar.reflacs.media;

public final class Flac {
	private final String name;

	private Flac(final String name) {
		this.name = name;
	}

	public static Flac of(final String name) {
		return new Flac(name);
	}

	@Override
	public String toString() {
		return name;
	}

	public String name() {
		return name;
	}
}
