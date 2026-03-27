package net.cbojar.reflacs.media;

public class Converted {
	private final String name;
	private final byte[] data;

	private Converted(final String name, final byte[] data) {
		this.name = name;
		this.data = data;
	}

	public static Converted of(final String name, final byte[] data) {
		return new Converted(name, data);
	}

	public String name() {
		return name;
	}

	public byte[] bytes() {
		return data;
	}

	@Override
	public String toString() {
		return name;
	}
}
