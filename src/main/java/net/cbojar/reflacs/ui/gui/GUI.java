package net.cbojar.reflacs.ui.gui;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.cbojar.reflacs.files.FilesCollector;
import net.cbojar.reflacs.files.FilesDistributor;
import net.cbojar.reflacs.ui.OnReady;
import net.cbojar.reflacs.ui.UI;

public final class GUI implements UI {
	private final MainWindow window;

	private GUI(final MainWindow window) {
		this.window = window;
	}

	public static UI show() {
		final ExecutorService executor = Executors.newCachedThreadPool();
		final MainWindow window = MainWindow.create(executor::execute);
		window.setVisible(true);

		return new GUI(window);
	}

	@Override
	public void whenReady(final OnReady onReady) throws IOException {
		window.ready();
		onReady.ready(FilesCollector.from(window.sourcePath()), FilesDistributor.to(window.destinationPath()));
	}
}
