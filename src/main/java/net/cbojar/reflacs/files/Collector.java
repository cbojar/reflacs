package net.cbojar.reflacs.files;

public interface Collector<K> {
	Iterable<Flac<K>> collect();
}
