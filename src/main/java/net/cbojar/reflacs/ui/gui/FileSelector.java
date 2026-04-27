package net.cbojar.reflacs.ui.gui;

import java.awt.Component;

import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

final class FileSelector implements AsComponent {
	private final JSplitPane split;

	private FileSelector(final JSplitPane split) {
		this.split = split;
	}

	public static FileSelector create(final JobManager jobs, final Paths paths) {
		final FileTree sourceTree = FileTree.create(jobs).addPathChangedListener(paths::updateSource);
		final FileTree destinationTree = FileTree.create(jobs).addPathChangedListener(paths::updateDestination);

		return new FileSelector(createSplit(sourceTree, destinationTree));
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

	public void ensureLayout() {
		split.setDividerLocation(0.5);
	}

	@Override
	public Component asComponent() {
		return split;
	}
}
