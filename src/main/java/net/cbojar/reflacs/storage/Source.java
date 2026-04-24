package net.cbojar.reflacs.storage;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;

public final class Source {
	private final Path key;
	private final byte[] data;

	private Source(final Path key, final byte[] data) {
		this.key = key;
		this.data = data;
	}

	public static Source of(final Path key, final byte[] data) {
		return new Source(key, data);
	}

	public Path key() {
		return key;
	}

	public void writeTo(final OutputStream out) throws IOException {
		out.write(data);
	}

	@Override
	public String toString() {
		return key.toString();
	}
}
