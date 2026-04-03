package net.cbojar.reflacs.files;

import net.cbojar.reflacs.media.MediaData;

public final class Flac<K> {
	private final K key;
	private final MediaData data;

	private Flac(final K key, final MediaData data) {
		this.key = key;
		this.data = data;
	}

	public static <K> Flac<K> of(final K key, final MediaData data) {
		return new Flac<>(key, data);
	}

	public static <K> Flac<K> of(final K key, final byte[] data) {
		return of(key, MediaData.flac(data));
	}

	public String name() {
		return key.toString();
	}

	public K key() {
		return key;
	}

	public MediaData data() {
		return data;
	}

	@Override
	public String toString() {
		return key.toString();
	}
}
