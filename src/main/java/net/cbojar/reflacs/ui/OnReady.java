package net.cbojar.reflacs.ui;

import java.io.IOException;

import net.cbojar.reflacs.storage.Collector;
import net.cbojar.reflacs.storage.Distributor;

public interface OnReady {
	void ready(final Collector collector, final Distributor distributor) throws IOException;
}
