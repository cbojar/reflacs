package net.cbojar.reflacs.ui;

import java.io.IOException;

public interface UI {
	void whenReady(final OnReady onReady) throws IOException;
}
