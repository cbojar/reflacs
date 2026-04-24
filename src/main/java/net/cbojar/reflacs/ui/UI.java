package net.cbojar.reflacs.ui;

import java.io.Closeable;
import java.io.IOException;

public interface UI extends Closeable {
	static UIBuilder build() {
		return new UIBuilder();
	}

	void run() throws IOException;
}
