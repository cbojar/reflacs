package net.cbojar.reflacs.transform;

import java.nio.file.Path;

public final class Output {
	private final Path name;
	private final byte[] data;

	private Output(final Path name, final byte[] data) {
		this.name = name;
		this.data = data;
	}

	public static Output of(final Path name, final byte[] data) {
		return new Output(name, data);
	}

	public Path name() {
		return name;
	}

	public byte[] data() {
		return data;
	}
}
