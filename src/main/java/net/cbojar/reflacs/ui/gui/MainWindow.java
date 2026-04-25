package net.cbojar.reflacs.ui.gui;

import java.awt.BorderLayout;
import java.awt.Component;
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

	public void setVisible(final boolean visible) {
		window.setVisible(visible);
	}
}
