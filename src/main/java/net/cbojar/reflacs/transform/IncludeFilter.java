package net.cbojar.reflacs.transform;

import java.io.IOException;

public interface IncludeFilter {
	boolean include(final Filterables filterables) throws IOException;
}
