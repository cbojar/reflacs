package net.cbojar.reflacs.ui.cli;

import java.io.IOException;
import java.nio.file.Path;

import net.cbojar.reflacs.files.FilesCollector;
import net.cbojar.reflacs.files.FilesDistributor;
import net.cbojar.reflacs.ui.OnReady;
import net.cbojar.reflacs.ui.UI;

public final class CLI implements UI {
	private final Path sourceRoot;
	private final Path destinationRoot;

	private CLI(final Path sourceRoot, final Path destinationRoot) {
		this.sourceRoot = sourceRoot;
		this.destinationRoot = destinationRoot;
	}

	public static UI fromArgs(final String... args) {
		final String sourceRoot = args[0];
		final String destinationRoot = args[1];

		return new CLI(Path.of(sourceRoot), Path.of(destinationRoot));
	}

	@Override
	public void whenReady(final OnReady onReady) throws IOException {
		onReady.ready(FilesCollector.from(sourceRoot), FilesDistributor.to(destinationRoot));
	}

	@Override
	public String toString() {
		return String.format("Source: %s, Destination: %s", sourceRoot, destinationRoot);
	}
}
