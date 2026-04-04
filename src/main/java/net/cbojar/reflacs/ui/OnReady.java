package net.cbojar.reflacs.ui;

import java.io.IOException;

import net.cbojar.reflacs.storage.Collector;
import net.cbojar.reflacs.storage.Distributor;

public interface OnReady<K> {
	void ready(final Collector<K> collector, final Distributor<K> distributor) throws IOException;
}
