package net.cbojar.reflacs.ui.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

final class MainWindow {
	private final JFrame window;
	private final JobManager jobs;
	private final JButton convertButton;
	private final CompletableFuture<Void> future = new CompletableFuture<>();

	private MainWindow(final JFrame window, final JobManager jobs, final JButton convertButton) {
		this.window = window;
		this.jobs = jobs;
		this.convertButton = convertButton;
	}

	public static MainWindow create(final JobManager jobs) {
		final JFrame window = new JFrame();
		window.setTitle("reflacs");
		window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		window.setMinimumSize(new Dimension(800, 600));
		window.setLayout(new BorderLayout());

		final JButton convertButton = new JButton("Convert");

		final MainWindow mainWindow = new MainWindow(window, jobs, convertButton);
		mainWindow.addSouth(convertButton);

		convertButton.addActionListener(event -> mainWindow.readyToConvert());

		return mainWindow;
	}

	public void addCenter(final AsComponent asComponent) {
		window.add(asComponent.asComponent(), BorderLayout.CENTER);
	}

	public void addCenter(final Component component) {
		window.add(component, BorderLayout.CENTER);
	}

	public void addSouth(final AsComponent asComponent) {
		window.add(asComponent.asComponent(), BorderLayout.SOUTH);
	}

	public void addSouth(final Component component) {
		window.add(component, BorderLayout.SOUTH);
	}

	public void addOpenListener(final Consumer<WindowEvent> listener) {
		window.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(final WindowEvent event) {
				listener.accept(event);
			}
		});
	}

	public void addCloseListener(final Consumer<WindowEvent> listener) {
		window.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(final WindowEvent event) {
				listener.accept(event);
			}
		});
	}

	public void pack() {
		window.pack();
	}

	private void readyToConvert() {
		jobs.runForUI(() -> convertButton.setEnabled(false));
		future.complete(null);
	}

	public void setVisible(final boolean visible) {
		jobs.runForUI(() -> window.setVisible(visible));
	}

	public void ready() throws IOException {
		try {
			future.get();
		} catch (final InterruptedException | ExecutionException ex) {
			throw new IOException(ex);
		}
	}
}
