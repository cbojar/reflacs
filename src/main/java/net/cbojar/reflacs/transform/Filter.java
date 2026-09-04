package net.cbojar.reflacs.transform;

import java.io.IOException;

public interface Filter {
	boolean test(final Filterables filterables) throws IOException;

	default Filter negate() {
		return filterables -> !test(filterables);
	}
}
