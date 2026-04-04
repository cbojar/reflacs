package net.cbojar.reflacs.ui;

import java.io.IOException;

public interface UI<K> {
	void whenReady(final OnReady<K> onReady) throws IOException;
}
