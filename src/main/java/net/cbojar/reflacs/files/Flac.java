package net.cbojar.reflacs.files;

import java.nio.file.Path;

import net.cbojar.reflacs.media.MediaData;

public final class Flac {
	private final Path path;
	private final MediaData data;

	private Flac(final Path path, final MediaData data) {
		this.path = path;
		this.data = data;
	}

	public static Flac of(final Path path, final MediaData data) {
		return new Flac(path, data);
	}

	public static Flac of(final Path path, final byte[] data) {
		return of(path, MediaData.flac(data));
	}

	public String name() {
		return path.toString();
	}

	public Path path() {
		return path;
	}

	public MediaData data() {
		return data;
	}

	@Override
	public String toString() {
		return path.toString();
	}
}
