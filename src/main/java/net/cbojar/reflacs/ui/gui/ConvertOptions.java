package net.cbojar.reflacs.ui.gui;

import java.util.concurrent.atomic.AtomicBoolean;

public final class ConvertOptions {
	private final AtomicBoolean overwrite;
	private final AtomicBoolean safeNames;

	private ConvertOptions() {
		overwrite = new AtomicBoolean(true);
		safeNames = new AtomicBoolean(false);
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

	public boolean safeNames() {
		return safeNames.get();
	}

	void safeNames(final boolean newValue) {
		safeNames.set(newValue);
	}
}
