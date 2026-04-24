package net.cbojar.reflacs.storage;

import java.io.IOException;

import net.cbojar.reflacs.formats.Format;

public interface Distributor {
	Format format();
	void distribute(final Destination media) throws IOException;
}
