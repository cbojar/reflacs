package net.cbojar.reflacs.ui.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;

final class PathChooser {
	private final JobManager jobs;
	private final Messages messages;
	private final JPanel panel;
	private final JLabel text;
	private final List<PathListener> listeners = new ArrayList<>();

	private PathChooser(final JobManager jobs, final Messages messages, final JPanel panel, final JLabel text) {
		this.jobs = jobs;
		this.messages = messages;
		this.panel = panel;
		this.text = text;
	}

	public static PathChooser create(final JobManager jobs, final Messages messages) {
		final JPanel panel = new JPanel(new BorderLayout());
		final JLabel text = new JLabel("");
		final JButton button = new JButton("Path...");

		panel.add(text, BorderLayout.CENTER);
		panel.add(button, BorderLayout.EAST);

		final PathChooser pathChooser = new PathChooser(jobs, messages, panel, text);

		button.addActionListener(event -> pathChooser.buttonClicked());

		return pathChooser;
	}

	private void buttonClicked() {
		final JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("."));
		chooser.setDialogTitle("Choose a directory");
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);

		if (chooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) {
			return;
		}

		final File chosen = chooser.getSelectedFile();

		if (!chosen.isDirectory()) {
			messages.showError("Invalid directory selection", String.format("\"%s\" is not a directory", chosen));
			return;
		}

		final Path path = chosen.toPath().toAbsolutePath();

		text.setText(path.toString());

		messages.showInfo("Path chosen", path.toString());

		fireListeners(path);
	}

	public Path path() {
		return Path.of(text.getText());
	}

	public void addListener(final PathListener listener) {
		listeners.add(listener);
	}

	private void fireListeners(final Path newPath) {
		for (final PathListener listener : listeners) {
			jobs.run(() -> listener.pathChanged(newPath));
		}
	}

	public Component asComponent() {
		return panel;
	}
}
