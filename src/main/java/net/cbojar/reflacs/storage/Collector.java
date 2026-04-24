package net.cbojar.reflacs.storage;

import java.io.IOException;

public interface Collector {
	Iterable<Source> collect() throws IOException;
}
