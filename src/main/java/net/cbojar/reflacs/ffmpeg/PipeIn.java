package net.cbojar.reflacs.ffmpeg;

import java.io.IOException;
import java.io.OutputStream;

@FunctionalInterface
interface PipeIn {
	void pipe(final OutputStream output) throws IOException;
}
