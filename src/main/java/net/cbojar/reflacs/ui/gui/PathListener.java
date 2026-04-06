package net.cbojar.reflacs.ui.gui;

import java.nio.file.Path;

@FunctionalInterface
interface PathListener {
	void pathChanged(final Path newPath);
}
