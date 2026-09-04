package net.cbojar.reflacs.transform;

import java.nio.file.Path;

public interface Renamer {
	public static final Renamer NOOP = path -> path;

	Path rename(final Path path);
}
