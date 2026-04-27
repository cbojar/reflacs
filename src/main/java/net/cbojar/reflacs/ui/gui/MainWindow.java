package net.cbojar.reflacs.ui.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.function.Consumer;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

final class MainWindow {
	private final JFrame window;

	private MainWindow(final JFrame window) {
		this.window = window;
	}

	public static MainWindow create() {
		final JFrame window = new JFrame();
		window.setTitle("reflacs");
		window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		window.setMinimumSize(new Dimension(800, 600));
		window.setLayout(new BorderLayout());

		return new MainWindow(window);
	}

	public MainWindow addMessages(final Messages messages) {
		window.add(messages.asComponent(), BorderLayout.NORTH);
		return this;
	}

	public MainWindow addFileSelector(final FileSelector selector) {
		window.add(selector.asComponent(), BorderLayout.CENTER);
		return this;
	}

	public MainWindow addConvertControls(final ConvertControls controls) {
		window.add(controls.asComponent(), BorderLayout.SOUTH);
		return this;
	}

	public MainWindow addOpenListener(final Consumer<WindowEvent> listener) {
		window.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(final WindowEvent event) {
				listener.accept(event);
			}
		});

		return this;
	}

	public MainWindow addCloseListener(final Consumer<WindowEvent> listener) {
		window.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(final WindowEvent event) {
				listener.accept(event);
			}
		});

		return this;
	}

	public MainWindow pack() {
		window.pack();
		return this;
	}

	public void setVisible(final boolean visible) {
		window.setVisible(visible);
	}
}
