package net.cbojar.reflacs.media;

import java.io.IOException;
import java.io.OutputStream;

public final class MediaData {
	private final byte[] data;

	private MediaData(final byte[] data) {
		this.data = data;
	}

	public static MediaData of(final byte[] data) {
		return new MediaData(data);
	}

	public void writeTo(final OutputStream out) throws IOException {
		out.write(data);
	}
}
