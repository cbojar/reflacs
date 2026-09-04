package net.cbojar.reflacs.transform;

import java.io.IOException;
import java.nio.file.Path;

import net.cbojar.reflacs.formats.Format;

public interface Destination {
	Format readFormat() throws IOException;
	boolean exists(final Path path);
	void write(final Output output) throws IOException;
}
