package net.cbojar.reflacs.media;

public interface Collector<K> {
	Iterable<Media<K>> collect();
}
