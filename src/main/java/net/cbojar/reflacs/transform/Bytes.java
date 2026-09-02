package net.cbojar.reflacs.transform;

import java.io.IOException;
import java.io.OutputStream;

public final class Bytes {
	private final byte[] data;

	private Bytes(final byte[] data) {
		this.data = data;
	}

	public static Bytes of(final byte[] data) {
		return new Bytes(data);
	}

	public void writeTo(final OutputStream out) throws IOException {
		out.write(data);
	}
}
