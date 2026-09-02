package net.cbojar.reflacs.ffmpeg;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

import net.cbojar.reflacs.formats.Format;
import net.cbojar.reflacs.transform.Bytes;
import net.cbojar.reflacs.transform.Transform;

public final class FFMPEG implements Transform {
	private static final FFMPEG INSTANCE = new FFMPEG();
	private FFMPEG() {
		// Prevent external instantiation
	}

	public static FFMPEG instance() {
		return INSTANCE;
	}

	@Override
	public Bytes transform(final Format format, final Bytes source) throws IOException {
		final AtomicReference<byte[]> bytesCaptor = new AtomicReference<>(new byte[0]);

		Run.start(Command.build(format)).withBlock(pipes -> {
			pipes.pipeIn(source::writeTo);
			pipes.pipeOut(inputStream -> bytesCaptor.set(inputStream.readAllBytes()));
		});

		return Bytes.of(bytesCaptor.get());
	}
}
