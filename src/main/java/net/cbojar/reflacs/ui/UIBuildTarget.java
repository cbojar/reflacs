package net.cbojar.reflacs.ui;

public interface UIBuildTarget {
	UI build(final String[] args, final OnReady onReady);
}
