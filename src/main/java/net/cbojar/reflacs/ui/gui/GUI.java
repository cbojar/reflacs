package net.cbojar.reflacs.ui.gui;

import java.io.IOException;
import net.cbojar.reflacs.files.FilesCollector;
import net.cbojar.reflacs.files.FilesDistributor;
import net.cbojar.reflacs.ui.OnReady;
import net.cbojar.reflacs.ui.UI;
import net.cbojar.reflacs.ui.UIBuildTarget;

public final class GUI implements UI {
	private final MainWindow window;
	private final JobManager jobs;
	private final OnReady onReady;

	private GUI(final MainWindow window, final JobManager jobs, final OnReady onReady) {
		this.window = window;
		this.jobs = jobs;
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

	@Override
	public void close() {
		jobs.close();
	}

	private static final class BuildTarget implements UIBuildTarget{
		@Override
		@SuppressWarnings("resource")
		public UI build(final String[] args, final OnReady onReady) {
			final JobManager jobs = JobManager.create();
			final MainWindow window = MainWindow.create(jobs);

			return new GUI(window, jobs, onReady);
		}
	}
}
