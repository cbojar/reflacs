package net.cbojar.reflacs.formats;

import java.util.Optional;

import net.cbojar.reflacs.configuration.Options;

/**
 * Bitrate for OGGs seems to bottom out at 64K, and anything below that will result in an error.
 * @see <a href="https://trac.ffmpeg.org/wiki/TheoraVorbisEncodingGuide">https://trac.ffmpeg.org/wiki/TheoraVorbisEncodingGuide</a>
 */
final class Ogg implements Format {
	private final Options options;

	Ogg(final Options options) {
		this.options = options;
	}

	@Override
	public String format() {
		return "ogg";
	}

	@Override
	public String extension() {
		return "ogg";
	}

	@Override
	public Optional<String> codec() {
		return Optional.of("libvorbis");
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
