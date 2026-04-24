package net.cbojar.reflacs.ui.gui;

import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicReference;

import net.cbojar.reflacs.files.FilesCollector;
import net.cbojar.reflacs.files.FilesDistributor;
import net.cbojar.reflacs.ui.OnReady;
import net.cbojar.reflacs.ui.UI;
import net.cbojar.reflacs.ui.UIBuildTarget;

public final class GUI implements UI {
	private final AtomicReference<Path> sourcePath;
	private final AtomicReference<Path> destinationPath;
	private final MainWindow window;
	private final JobManager jobs;
	private final OnReady onReady;

	private GUI(
			final AtomicReference<Path> sourcePath,
			final AtomicReference<Path> destinationPath,
			final MainWindow window,
			final JobManager jobs,
			final OnReady onReady) {
		this.sourcePath = sourcePath;
		this.destinationPath = destinationPath;
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
		onReady.ready(FilesCollector.from(sourcePath.get()), FilesDistributor.to(destinationPath.get()));
	}

	@Override
	public void close() {
		jobs.close();
	}

	private static final class BuildTarget implements UIBuildTarget{
		@Override
		@SuppressWarnings("resource")
		public UI build(final String[] args, final OnReady onReady) {
			final AtomicReference<Path> sourcePath = new AtomicReference<>();
			final AtomicReference<Path> destinationPath = new AtomicReference<>();
			final JobManager jobs = JobManager.create();
			final MainWindow window = MainWindow.create(jobs);
			final GUI gui = new GUI(sourcePath, destinationPath, window, jobs, onReady);

			window.addSourcePathChangedListener(sourcePath::set);
			window.addDestinationPathChangedListener(destinationPath::set);

			return gui;
		}
	}
}
