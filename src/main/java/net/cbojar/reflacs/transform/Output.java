package net.cbojar.reflacs.transform;

import java.nio.file.Path;

public final class Output {
	private final Path name;
	private final Bytes data;

	private Output(final Path name, final Bytes data) {
		this.name = name;
		this.data = data;
	}

	public static Output of(final Path name, final byte[] data) {
		return of(name, Bytes.of(data));
	}

	public static Output of(final Path name, final Bytes data) {
		return new Output(name, data);
	}

	public Path name() {
		return name;
	}

	public Bytes data() {
		return data;
	}
}
