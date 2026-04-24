package net.cbojar.reflacs.ui.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;

final class FileTree implements AsComponent {
	private final JPanel panel;
	private final PathChooser path;
	private final JTree tree;

	private FileTree(final JPanel panel, final PathChooser path, final JTree tree) {
		this.panel = panel;
		this.path = path;
		this.tree = tree;
	}

	public static FileTree create(final JobManager jobs) {
		final JPanel panel = new JPanel(new BorderLayout());
		final PathChooser path = PathChooser.create(jobs);
		final JTree tree = new JTree(new DefaultTreeModel(null));

		panel.add(path.asComponent(), BorderLayout.NORTH);
		panel.add(tree, BorderLayout.CENTER);

		final FileTree fileTree = new FileTree(panel, path, tree);

		path.addListener(fileTree::refresh);

		return fileTree;
	}

	public void addPathChangedListener(final PathListener listener) {
		path.addListener(listener);
	}

	private void refresh(final Path newPath) {
		try {
			((DefaultTreeModel)tree.getModel()).setRoot(PathTreeNode.create(newPath, false, getChildren(newPath)));
		} catch (final UncheckedIOException ex) {
			ex.printStackTrace(); // TODO: make better
		}
	}

	private static List<PathTreeNode> getChildren(final Path path) {
		if (Files.isRegularFile(path)) {
			return Collections.emptyList();
		}

		try (Stream<Path> paths = Files.list(path)) {
			return paths.sorted()
				.map(p -> PathTreeNode.create(p.getFileName(), Files.isRegularFile(p), getChildren(p)))
				.toList();
		} catch (final IOException ex) {
			throw new UncheckedIOException(ex);
		}
	}

	public Path path() {
		return path.path();
	}

	@Override
	public Component asComponent() {
		return panel;
	}
}
