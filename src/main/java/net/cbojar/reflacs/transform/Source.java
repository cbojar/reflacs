package net.cbojar.reflacs.transform;

import java.io.IOException;

public interface Source {
	Iterable<Input> inputs() throws IOException;
}
