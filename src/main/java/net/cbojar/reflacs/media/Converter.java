package net.cbojar.reflacs.media;

import java.io.IOException;

public interface Converter {
	Converted convert(Flac flac) throws IOException;
}
