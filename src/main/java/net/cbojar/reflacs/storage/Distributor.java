package net.cbojar.reflacs.storage;

import java.io.IOException;

import net.cbojar.reflacs.formats.Format;

public interface Distributor<K> {
	Format format();
	void distribute(final Destination<K> media) throws IOException;
}
