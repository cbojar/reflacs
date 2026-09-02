package net.cbojar.reflacs.ui;

import java.io.Closeable;
import java.io.IOException;

public interface UI extends Closeable {
	void run() throws IOException;
}
