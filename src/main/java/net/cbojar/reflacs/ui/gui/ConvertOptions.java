package net.cbojar.reflacs.ui.gui;

import java.util.concurrent.atomic.AtomicBoolean;

public final class ConvertOptions {
	private final AtomicBoolean overwrite;

	private ConvertOptions() {
		overwrite = new AtomicBoolean(true);
	}

	static ConvertOptions create() {
		return new ConvertOptions();
	}

	public boolean overwrite() {
		return overwrite.get();
	}

	void overwrite(final boolean newValue) {
		overwrite.set(newValue);
	}
}
