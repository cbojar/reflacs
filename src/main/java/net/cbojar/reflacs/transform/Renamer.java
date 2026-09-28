package net.cbojar.reflacs.transform;

import java.nio.file.Path;

public interface Renamer {
	Path rename(final Path path);
}
