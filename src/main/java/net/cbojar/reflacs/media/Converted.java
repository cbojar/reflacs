package net.cbojar.reflacs.media;

public class Converted {
	private final String name;

	private Converted(final String name) {
		this.name = name;
	}

	public static Converted of(final String name) {
		return new Converted(name);
	}

	@Override
	public String toString() {
		return name;
	}
}
