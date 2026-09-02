package net.cbojar.reflacs.files;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import net.cbojar.reflacs.storage.Collector;
import net.cbojar.reflacs.storage.Source;

public final class FilesCollector implements Collector {
	private final Path source;
	private final Iterable<Path> files;

	private FilesCollector(final Path source, final Iterable<Path> files) {
		this.source = source;
		this.files = files;
	}

	public static Collector from(final Path source, final Collection<Path> files) {
		return new FilesCollector(source, new ArrayList<Path>(files));
	}

	@Override
	public Iterable<Source> collect() throws IOException {
		return () -> new PathIterator(source, files.iterator());
	}
}
