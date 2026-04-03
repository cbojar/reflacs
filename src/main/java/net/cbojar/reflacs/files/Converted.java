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
		return new Converted<>(replaceFlacSuffix(path, data.type()), data);
	}

	private static Path replaceFlacSuffix(final Path path, final String outputFormat) {
		final String pathString = path.toString();
		return Path.of(String.format("%s.%s", pathString.substring(0, pathString.length() - 5), outputFormat));
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
