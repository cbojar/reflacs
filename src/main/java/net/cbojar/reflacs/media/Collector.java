package net.cbojar.reflacs.media;

public interface Collector<K> {
	Iterable<MediaData<K>> collect();
}
