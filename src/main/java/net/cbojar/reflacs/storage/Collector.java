package net.cbojar.reflacs.storage;

import net.cbojar.reflacs.media.Media;

public interface Collector<K> {
	Iterable<Media<K>> collect();
}
