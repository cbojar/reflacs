package net.cbojar.reflacs.transform;

import java.io.IOException;

import net.cbojar.reflacs.formats.Format;

public interface Transform {
	Bytes transform(final Format format, final Bytes source) throws IOException;
}
