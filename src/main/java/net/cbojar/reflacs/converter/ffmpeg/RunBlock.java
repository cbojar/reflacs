package net.cbojar.reflacs.converter.ffmpeg;

import java.io.IOException;

interface RunBlock {
	void execute(final Pipes pipes) throws IOException;
}
