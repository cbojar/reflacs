package net.cbojar.reflacs.ui.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

final class PathChooser {
	private final Consumer<Runnable> jobs;
	private final JPanel panel;
	private final JLabel text;
	private final List<PathListener> listeners = new ArrayList<>();

	private PathChooser(final Consumer<Runnable> jobs, final JPanel panel, final JLabel text) {
		this.jobs = jobs;
		this.panel = panel;
		this.text = text;
	}

	public static PathChooser create(final Consumer<Runnable> jobs) {
		final JPanel panel = new JPanel(new BorderLayout());
		final JLabel text = new JLabel("");
		final JButton button = new JButton("Path...");

		panel.add(text, BorderLayout.CENTER);
		panel.add(button, BorderLayout.EAST);

		final PathChooser pathChooser = new PathChooser(jobs, panel, text);

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
			showInvalidSelectionDialog();
			return;
		}

		final Path path = chosen.toPath().toAbsolutePath();

		text.setText(path.toString());

		fireListeners(path);
	}

	private static void showInvalidSelectionDialog() {
		final JLabel dialogText = new JLabel("Must select a directory");
		dialogText.setHorizontalAlignment(SwingConstants.CENTER);

		final JDialog dialog = new JDialog();
		dialog.setTitle("Invalid selection");
		dialog.setSize(300, 100);
		dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		dialog.setLocationRelativeTo(null);

		dialog.add(dialogText);

		dialog.setVisible(true);
	}

	public Path path() {
		return Path.of(text.getText());
	}

	public void addListener(final PathListener listener) {
		listeners.add(listener);
	}

	private void fireListeners(final Path newPath) {
		for (final PathListener listener : listeners) {
			jobs.accept(() -> listener.pathChanged(newPath));
		}
	}

	public Component asComponent() {
		return panel;
	}
}
