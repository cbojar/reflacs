package net.cbojar.reflacs.storage;

import java.io.IOException;

public interface Collector<K> {
	Iterable<Media<K>> collect() throws IOException;
}
