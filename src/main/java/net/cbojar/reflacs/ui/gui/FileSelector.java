package net.cbojar.reflacs.ui.gui;

import java.awt.Component;

import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

final class FileSelector {
	private final JSplitPane split;
	private final FileTree source;
	private final FileTree destination;

	private FileSelector(final JSplitPane split, final FileTree source, final FileTree destination) {
		this.split = split;
		this.source = source;
		this.destination = destination;
	}

	public static FileSelector create(final JobManager jobs) {
		final FileTree sourceTree = FileTree.create(jobs);
		final FileTree destinationTree = FileTree.create(jobs);

		return new FileSelector(createSplit(sourceTree, destinationTree), sourceTree, destinationTree);
	}

	private static JSplitPane createSplit(final FileTree left, final FileTree right) {
		final JSplitPane split = new JSplitPane(
			JSplitPane.HORIZONTAL_SPLIT,
			new JScrollPane(left.asComponent()),
			new JScrollPane(right.asComponent()));
		split.setOneTouchExpandable(true);
		split.setDividerLocation(0.5);
		split.setResizeWeight(0.5);

		return split;
	}

	public FileSelector addSourcePathChangedListener(final PathListener listener) {
		source.addPathChangedListener(listener);
		return this;
	}

	public FileSelector addDestinationPathChangedListener(final PathListener listener) {
		destination.addPathChangedListener(listener);
		return this;
	}

	public void ensureLayout() {
		split.setDividerLocation(0.5);
	}

	public Component asComponent() {
		return split;
	}
}
