package net.cbojar.reflacs.ui;

import java.io.IOException;

import net.cbojar.reflacs.storage.Collector;
import net.cbojar.reflacs.storage.Distributor;

public interface UI<K> {
	Collector<K> collector() throws IOException;
	Distributor<K> distributor() throws IOException;
}
