package net.cbojar.reflacs.converter.ffmpeg;

import java.io.IOException;
import java.io.InputStream;

@FunctionalInterface
interface PipeOut {
	void pipe(final InputStream input) throws IOException;
}
