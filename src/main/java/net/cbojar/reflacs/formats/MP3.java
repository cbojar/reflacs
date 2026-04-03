package net.cbojar.reflacs.formats;

import java.util.Optional;

/**
 * Bitrate for MP3s seems to bottom out at 32K, and anything below that will result in a 32K bitrate.
 * @see <a href="https://trac.ffmpeg.org/wiki/Encode/MP3">https://trac.ffmpeg.org/wiki/Encode/MP3</a>
 */
final class MP3 implements Format {
	private final Options options;

	MP3(final Options options) {
		this.options = options;
	}

	@Override
	public String format() {
		return "mp3";
	}

	@Override
	public String extension() {
		return "mp3";
	}

	@Override
	public Optional<String> codec() {
		return Optional.empty();
	}

	@Override
	public Optional<String> quality() {
		return options.get("quality");
	}

	@Override
	public Optional<String> bitrate() {
		return options.get("bitrate");
	}
}
