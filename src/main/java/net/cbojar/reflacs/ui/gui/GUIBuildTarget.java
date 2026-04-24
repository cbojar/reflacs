package net.cbojar.reflacs.ui.gui;

import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicReference;

import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import net.cbojar.reflacs.ui.OnReady;
import net.cbojar.reflacs.ui.UI;
import net.cbojar.reflacs.ui.UIBuildTarget;

final class GUIBuildTarget implements UIBuildTarget{
	@Override
	@SuppressWarnings("resource")
	public UI build(final String[] args, final OnReady onReady) {
		final AtomicReference<Path> sourcePath = new AtomicReference<>();
		final AtomicReference<Path> destinationPath = new AtomicReference<>();
		final JobManager jobs = JobManager.create();

		final FileTree sourceTree = FileTree.create(jobs);
		final FileTree destinationTree = FileTree.create(jobs);

		sourceTree.addPathChangedListener(sourcePath::set);
		destinationTree.addPathChangedListener(destinationPath::set);

		final JSplitPane split = createSplit(sourceTree, destinationTree);

		final MainWindow window = MainWindow.create(jobs);

		window.addCenter(split);

		window.addOpenListener(event -> split.setDividerLocation(0.5));

		window.pack();

		return new GUI(sourcePath, destinationPath, window, jobs, onReady);
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