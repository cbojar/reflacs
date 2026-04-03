package net.cbojar.reflacs.media;

public final class MediaData<K> {
	private final K key;
	private final String type;
	private final byte[] data;

	private MediaData(final K key, final String type, final byte[] data) {
		this.key = key;
		this.type = type;
		this.data = data;
	}

	public static <K> MediaData<K> of(final K key, final String type, final byte[] data) {
		return new MediaData<>(key, type, data);
	}

	public static <K> MediaData<K> flac(final K key, final byte[] data) {
		return of(key, "flac", data);
	}

	public K key() {
		return key;
	}

	public String type() {
		return type;
	}

	public byte[] bytes() {
		return data;
	}

	@Override
	public String toString() {
		return String.format("%s(%s)", key.toString(), type);
	}
}
