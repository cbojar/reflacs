package net.cbojar.reflacs.ui.gui;

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

		final Messages messages = Messages.create(jobs);

		final Paths paths = Paths.create();
		final FileSelector selector = FileSelector.create(jobs, messages)
			.addSourcePathChangedListener(paths::updateSource)
			.addDestinationPathChangedListener(paths::updateDestination);

		final ConvertControls convert = ConvertControls.create(jobs)
			.addConvertListener(() ->messages.reportErrors(() -> onReady.ready(
				FilesCollector.from(paths.source()), FilesDistributor.to(paths.destination()))));

		final MainWindow window = MainWindow.create()
			.addMessages(messages)
			.addFileSelector(selector)
			.addConvertControls(convert)
			.addOpenListener(event -> selector.ensureLayout())
			.addCloseListener(event -> await.complete(null))
			.pack();

		return new GUI(window, jobs, await);
	}
}
