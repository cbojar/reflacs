package net.cbojar.reflacs.ui.gui;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import javax.swing.JFileChooser;
import net.cbojar.reflacs.files.FilesCollector;
import net.cbojar.reflacs.files.FilesDistributor;
import net.cbojar.reflacs.storage.Collector;
import net.cbojar.reflacs.storage.Distributor;
import net.cbojar.reflacs.ui.UI;

public final class GUI implements UI<Path> {
	private final Path sourceRoot;
	private final Path destinationRoot;

	private GUI(final Path sourceRoot, final Path destinationRoot) {
		this.sourceRoot = sourceRoot;
		this.destinationRoot = destinationRoot;
	}

	public static UI<Path> show() throws IOException {
		final Path source = choosePath("source");
		final Path destination = choosePath("destination");

		return new GUI(source, destination);
	}

	private static Path choosePath(final String which) throws IOException {
		final JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("."));
		chooser.setDialogTitle(String.format("Choose a %s directory", which));
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);

		if (chooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) {
			throw new IOException(String.format("No %s chosen", which));
		}

		final File chosen = chooser.getSelectedFile();

		if (!chosen.isDirectory()) {
			throw new IOException(String.format("The %s must be a directory", which));
		}

		return chosen.toPath();
	}

	@Override
	public Collector<Path> collector() throws IOException {
		return FilesCollector.from(sourceRoot);
	}

	@Override
	public Distributor<Path> distributor() throws IOException {
		return FilesDistributor.to(destinationRoot);
	}

	@Override
	public String toString() {
		return String.format("Source: %s, Destination: %s", sourceRoot, destinationRoot);
	}
}
