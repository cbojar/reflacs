package net.cbojar.reflacs.ffmpeg;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import net.cbojar.reflacs.formats.Format;
import net.cbojar.reflacs.media.MediaData;

public final class FFMPEG {
	private final Format format;

	private FFMPEG(final Format format) {
		this.format = format;
	}

	public static FFMPEG of(final Format format) {
		return new FFMPEG(format);
	}

	public MediaData convert(final MediaData data) throws IOException {
		final ByteArrayOutputStream convertedBytes = new ByteArrayOutputStream();

		Run.start(Command.build(format)).withBlock(pipes -> {
			pipes.pipeIn(data::writeTo);
			pipes.pipeOut(inputStream -> convertedBytes.write(inputStream.readAllBytes()));
		});

		return MediaData.of(convertedBytes.toByteArray());
	}
}
