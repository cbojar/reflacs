package net.cbojar.reflacs.ui.gui;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.cbojar.reflacs.files.FilesCollector;
import net.cbojar.reflacs.files.FilesDistributor;
import net.cbojar.reflacs.ui.OnReady;
import net.cbojar.reflacs.ui.UI;
import net.cbojar.reflacs.ui.UIBuildTarget;

public final class GUI implements UI {
	private final MainWindow window;
	private final OnReady onReady;

	private GUI(final MainWindow window, final OnReady onReady) {
		this.window = window;
		this.onReady = onReady;
	}

	public static UIBuildTarget target() {
		return new GUI.BuildTarget();
	}

	@Override
	public void run() throws IOException {
		window.setVisible(true);
		window.ready();
		onReady.ready(FilesCollector.from(window.sourcePath()), FilesDistributor.to(window.destinationPath()));
	}

	private static final class BuildTarget implements UIBuildTarget{
		@Override
		public UI build(final String[] args, final OnReady onReady) {
			final ExecutorService executor = Executors.newCachedThreadPool();
			final MainWindow window = MainWindow.create(executor::execute);

			return new GUI(window, onReady);
		}
	}
}
