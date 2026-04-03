package net.cbojar.reflacs.storage;

import java.io.IOException;

public interface Collector<K> {
	Iterable<Source<K>> collect() throws IOException;
}
