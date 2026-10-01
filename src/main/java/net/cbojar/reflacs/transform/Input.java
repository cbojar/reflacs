package net.cbojar.reflacs.transform;

import java.nio.file.Path;

public final class Input {
	private final Path name;
	private final byte[] data;

	private Input(final Path name, final byte[] data) {
		this.name = name;
		this.data = data;
	}

	public static Input of(final Path name, final byte[] data) {
		return new Input(name, data);
	}

	public Path name() {
		return name;
	}

	public byte[] data() {
		return data;
	}
}
