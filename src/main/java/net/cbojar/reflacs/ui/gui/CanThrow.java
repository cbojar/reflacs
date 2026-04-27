package net.cbojar.reflacs.ui.gui;

interface CanThrow<T extends Throwable> {
	public void run() throws T;
}
