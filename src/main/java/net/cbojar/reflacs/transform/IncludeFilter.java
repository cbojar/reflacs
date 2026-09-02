package net.cbojar.reflacs.transform;

import java.io.IOException;

public interface IncludeFilter {
	boolean include(final IncludeFilterables filterables) throws IOException;
}
