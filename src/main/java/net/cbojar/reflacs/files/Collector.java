package net.cbojar.reflacs.files;

import net.cbojar.reflacs.media.MediaData;

public interface Collector<K> {
	Iterable<MediaData<K>> collect();
}
