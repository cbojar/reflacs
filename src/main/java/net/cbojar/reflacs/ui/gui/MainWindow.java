package net.cbojar.reflacs.ui.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.WindowConstants;

final class MainWindow {
	private final JFrame window;
	private final JobManager jobs;
	private final FileTree source;
	private final FileTree destination;
	private final JButton convertButton;
	private final CompletableFuture<Void> future = new CompletableFuture<>();

	private MainWindow(final JFrame window, final JobManager jobs, final FileTree source, final FileTree destination, final JButton convertButton) {
		this.window = window;
		this.jobs = jobs;
		this.source = source;
		this.destination = destination;
		this.convertButton = convertButton;
	}

	public static MainWindow create(final JobManager jobs) {
		final JFrame window = new JFrame();
		window.setTitle("reflacs");
		window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		window.setMinimumSize(new Dimension(800, 600));
		window.setLayout(new BorderLayout());

		final FileTree sourceTree = FileTree.create(jobs);
		final FileTree destinationTree = FileTree.create(jobs);

		final JSplitPane split = createSplit(sourceTree, destinationTree);
		final JButton convertButton = new JButton("Convert");

		final JPanel panel = new JPanel(new BorderLayout());
		panel.add(split, BorderLayout.CENTER);
		panel.add(convertButton, BorderLayout.SOUTH);

		window.add(panel, BorderLayout.CENTER);

		window.pack();
		window.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(final WindowEvent event) {
				split.setDividerLocation(0.5);
			}
		});

		final MainWindow mainWindow = new MainWindow(window, jobs, sourceTree, destinationTree, convertButton);

		convertButton.addActionListener(event -> mainWindow.readyToConvert());

		return mainWindow;
	}

	private void readyToConvert() {
		jobs.runForUI(() -> convertButton.setEnabled(false));
		future.complete(null);
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

	public void setVisible(final boolean visible) {
		jobs.runForUI(() -> window.setVisible(visible));
	}

	public Path sourcePath() {
		return source.path();
	}

	public Path destinationPath() {
		return destination.path();
	}

	public void ready() throws IOException {
		try {
			future.get();
		} catch (final InterruptedException | ExecutionException ex) {
			throw new IOException(ex);
		}
	}
}
