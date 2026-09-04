package net.cbojar.reflacs.transform;

import java.io.IOException;

import net.cbojar.reflacs.formats.Format;

public interface Destination {
	Format readFormat() throws IOException;
	void write(final Output output) throws IOException;
}
