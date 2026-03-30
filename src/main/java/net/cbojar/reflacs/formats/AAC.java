package net.cbojar.reflacs.formats;

import java.util.Optional;

import net.cbojar.reflacs.configuration.Options;

/**
 * @see <a href="https://trac.ffmpeg.org/wiki/Encode/AAC">https://trac.ffmpeg.org/wiki/Encode/AAC</a>
 */
final class AAC implements Format {
	private final Options options;

	AAC(final Options options) {
		this.options = options;
	}

	@Override
	public String format() {
		return "adts";
	}

	@Override
	public String extension() {
		return "aac";
	}

	@Override
	public Optional<String> codec() {
		return Optional.of("aac");
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
