package net.cbojar.reflacs.ui.gui;

import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

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

		final FileTree sourceTree = FileTree.create(jobs);
		final FileTree destinationTree = FileTree.create(jobs);

		final AtomicReference<Path> sourcePath = new AtomicReference<>();
		final AtomicReference<Path> destinationPath = new AtomicReference<>();

		sourceTree.addPathChangedListener(sourcePath::set);
		destinationTree.addPathChangedListener(destinationPath::set);

		final JSplitPane split = createSplit(sourceTree, destinationTree);

		final MainWindow window = MainWindow.create();

		window.addCenter(split);

		final JButton convertButton = new JButton("Convert");

		window.addSouth(convertButton);

		convertButton.addActionListener(event -> {
			jobs.runForUI(() -> convertButton.setEnabled(false));
			try {
				onReady.ready(FilesCollector.from(sourcePath.get()), FilesDistributor.to(destinationPath.get()));
			} catch (final IOException ex) {
				ex.printStackTrace(); // TODO Fix error handling
			}
			jobs.runForUI(() -> convertButton.setEnabled(true));
		});

		window.addOpenListener(event -> split.setDividerLocation(0.5));
		window.addCloseListener(event -> await.complete(null));

		window.pack();

		return new GUI(window, jobs, await);
	}

	private static JSplitPane createSplit(final AsComponent left, final AsComponent right) {
		final JSplitPane split = new JSplitPane(
			JSplitPane.HORIZONTAL_SPLIT,
			new JScrollPane(left.asComponent()),
			new JScrollPane(right.asComponent()));
		split.setOneTouchExpandable(true);
		split.setDividerLocation(0.5);
		split.setResizeWeight(0.5);

		return split;
	}
}