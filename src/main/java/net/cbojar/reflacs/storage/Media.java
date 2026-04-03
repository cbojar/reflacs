package net.cbojar.reflacs.storage;

import java.io.IOException;
import java.io.OutputStream;

public final class Media<K> {
	private final K key;
	private final byte[] data;

	private Media(final K key, final byte[] data) {
		this.key = key;
		this.data = data;
	}

	public static <K> Media<K> of(final K key, final byte[] data) {
		return new Media<>(key, data);
	}

	public K key() {
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
