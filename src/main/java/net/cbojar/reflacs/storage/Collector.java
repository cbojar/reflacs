package net.cbojar.reflacs.storage;

import java.io.IOException;

import net.cbojar.reflacs.media.Media;

public interface Collector<K> {
	Iterable<Media<K>> collect() throws IOException;
}
