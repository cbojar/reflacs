package net.cbojar.reflacs.ui.gui;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import net.cbojar.reflacs.files.FilesCollector;
import net.cbojar.reflacs.files.FilesDistributor;
import net.cbojar.reflacs.ui.OnReady;
import net.cbojar.reflacs.ui.UI;
import net.cbojar.reflacs.ui.UIBuildTarget;

final class GUIBuildTarget implements UIBuildTarget{
	@Override
	@SuppressWarnings("resource")
	public UI build(final String[] args, final OnReady onReady) {
		final JobManager jobs = JobManager.create();
		final CompletableFuture<Void> await = new CompletableFuture<>();

		final MainWindow window = MainWindow.create();

		final Paths paths = Paths.create();
		final FileSelector selector = FileSelector.create(jobs, paths);

		window.addCenter(selector);

		final ConvertControls convert = ConvertControls.create(jobs)
			.addConvertListener(() -> {
				try {
					onReady.ready(FilesCollector.from(paths.source()), FilesDistributor.to(paths.destination()));
				} catch (final IOException ex) {
					ex.printStackTrace(); // TODO Fix error handling
				}
			});

		window.addSouth(convert);

		window.addOpenListener(event -> selector.ensureLayout());
		window.addCloseListener(event -> await.complete(null));

		window.pack();

		return new GUI(window, jobs, await);
	}
}
