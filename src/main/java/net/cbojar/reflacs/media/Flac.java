package net.cbojar.reflacs.media;

public final class Flac {
	private final String name;
	private final byte[] data;

	private Flac(final String name, final byte[] data) {
		this.name = name;
		this.data = data;
	}

	public static Flac of(final String name, final byte[] data) {
		return new Flac(name, data);
	}

	public byte[] bytes() {
		return data;
	}

	@Override
	public String toString() {
		return name;
	}

	public String name() {
		return name;
	}
}
