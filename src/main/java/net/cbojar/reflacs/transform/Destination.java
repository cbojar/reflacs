package net.cbojar.reflacs.transform;

import java.io.IOException;

public interface Destination {
	void write(final Output output) throws IOException;
}
