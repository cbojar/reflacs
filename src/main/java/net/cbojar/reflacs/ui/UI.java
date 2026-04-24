package net.cbojar.reflacs.ui;

import java.io.IOException;

public interface UI {
	static UIBuilder build() {
		return new UIBuilder();
	}

	void run() throws IOException;
}
