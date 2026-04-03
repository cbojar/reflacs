package net.cbojar.reflacs.files;

import net.cbojar.reflacs.media.MediaData;

public final class Flac<K> {
	private final MediaData<K> data;

	private Flac(final MediaData<K> data) {
		this.data = data;
	}

	public static <K> Flac<K> of(final MediaData<K> data) {
		return new Flac<>(data);
	}

	public static <K> Flac<K> of(final K key, final byte[] data) {
		return of(MediaData.flac(key, data));
	}

	public K key() {
		return data.key();
	}

	public MediaData<K> data() {
		return data;
	}

	@Override
	public String toString() {
		return data.key().toString();
	}
}
