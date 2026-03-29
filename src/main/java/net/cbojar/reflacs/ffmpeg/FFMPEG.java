package net.cbojar.reflacs.ffmpeg;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import net.cbojar.reflacs.media.MediaData;

public final class FFMPEG {
	private final FFMPEGConfiguration configuration;

	private FFMPEG(final FFMPEGConfiguration configuration) {
		this.configuration = configuration;
	}

	public static FFMPEG of(final FFMPEGConfiguration configuration) {
		return new FFMPEG(configuration);
	}

	public MediaData convert(final MediaData flac) throws IOException {
		final ByteArrayOutputStream convertedBytes = new ByteArrayOutputStream();

		Run.start(Command.build(configuration)).withBlock(pipes -> {
			pipes.pipeIn(outputStream -> outputStream.write(flac.bytes()));
			pipes.pipeOut(inputStream -> convertedBytes.write(inputStream.readAllBytes()));
		});

		return MediaData.of(configuration.outputFormat(), convertedBytes.toByteArray());
	}
}
