package net.cbojar.reflacs.transform;

import java.io.IOException;

import net.cbojar.reflacs.formats.Format;

public interface Transform {
	byte[] transform(final Format format, final byte[] source) throws IOException;
}
