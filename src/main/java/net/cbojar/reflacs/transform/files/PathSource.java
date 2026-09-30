package net.cbojar.reflacs.transform.files;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import net.cbojar.reflacs.transform.Input;
import net.cbojar.reflacs.transform.Source;

public final class PathSource implements Source {
	private final PathStore store;
	private final Path source;

	private PathSource(final PathStore store, final Path source) {
		this.store = store;
		this.source = source;
	}

	public static Source from(final PathStore store, final Path source) {
		return new PathSource(store, source);
	}

	@Override
	public Iterable<Input> inputs() throws IOException {
		final List<Path> flacs = store.findFlacFiles(source);
		return () -> new PathIterator(store, source, flacs.iterator());
	}
}
