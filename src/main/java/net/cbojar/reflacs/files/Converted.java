package net.cbojar.reflacs.files;

import java.nio.file.Path;

import net.cbojar.reflacs.media.MediaData;

public class Converted<K> {
	private final Path path;
	private final MediaData<K> data;

	private Converted(final Path path, final MediaData<K> data) {
		this.path = path;
		this.data = data;
	}

	public static <K> Converted<K> of(final Path path, final MediaData<K> data) {
		return new Converted<>(path, data);
	}

	public Path path() {
		return path;
	}

	public byte[] bytes() {
		return data.bytes();
	}

	@Override
	public String toString() {
		return path.toString();
	}
}
