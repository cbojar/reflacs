package net.cbojar.reflacs.ffmpeg;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

import net.cbojar.reflacs.formats.Format;
import net.cbojar.reflacs.storage.Destination;
import net.cbojar.reflacs.storage.Source;

public final class FFMPEG {
	private final Format format;

	private FFMPEG(final Format format) {
		this.format = format;
	}

	public static FFMPEG of(final Format format) {
		return new FFMPEG(format);
	}

	public Destination convert(final Source source) throws IOException {
		final AtomicReference<byte[]> bytesCaptor = new AtomicReference<>(new byte[0]);

		Run.start(Command.build(format)).withBlock(pipes -> {
			pipes.pipeIn(source::writeTo);
			pipes.pipeOut(inputStream -> bytesCaptor.set(inputStream.readAllBytes()));
		});

		return Destination.of(source.key(), bytesCaptor.get());
	}
}
