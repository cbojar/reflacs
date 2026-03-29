package net.cbojar.reflacs.media;

public final class MediaData {
	private final String type;
	private final byte[] data;

	private MediaData(final String type, final byte[] data) {
		this.type = type;
		this.data = data;
	}

	public static MediaData of(final String type, final byte[] data) {
		return new MediaData(type, data);
	}

	public static MediaData flac(final byte[] data) {
		return of("flac", data);
	}

	public String type() {
		return type;
	}

	public byte[] bytes() {
		return data;
	}
}
