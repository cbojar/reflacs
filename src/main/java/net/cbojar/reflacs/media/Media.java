package net.cbojar.reflacs.media;

import java.io.IOException;
import java.io.OutputStream;

public final class Media<K> {
	private final K key;
	private final MediaData data;

	private Media(final K key, final MediaData data) {
		this.key = key;
		this.data = data;
	}

	public static <K> Media<K> of(final K key, final MediaData data) {
		return new Media<>(key, data);
	}

	public K key() {
		return key;
	}

	public MediaData data() {
		return data;
	}

	public void writeDataTo(final OutputStream out) throws IOException {
		data.writeTo(out);
	}

	@Override
	public String toString() {
		return key.toString();
	}
}
