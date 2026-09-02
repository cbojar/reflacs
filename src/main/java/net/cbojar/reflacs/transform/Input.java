package net.cbojar.reflacs.transform;

import java.nio.file.Path;

public final class Input {
	private final Path name;
	private final Bytes data;

	private Input(final Path name, final Bytes data) {
		this.name = name;
		this.data = data;
	}

	public static Input of(final Path name, final byte[] data) {
		return new Input(name, Bytes.of(data));
	}

	public Path name() {
		return name;
	}

	public Bytes data() {
		return data;
	}
}
