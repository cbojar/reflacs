package net.cbojar.reflacs.ui;

public final class UIBuilder {
	private String[] args = {};
	private OnReady onReady;

	UIBuilder() {
		// Nothing to do
	}

	public UIBuilder args(final String... args) {
		this.args = args;
		return this;
	}

	public UIBuilder onReady(final OnReady onReady) {
		this.onReady = onReady;
		return this;
	}

	public UI buildInto(final UIBuildTarget target) {
		return target.build(args, onReady);
	}
}
