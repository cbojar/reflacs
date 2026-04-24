package net.cbojar.reflacs.ui.cli;

import java.io.IOException;
import java.nio.file.Path;

import net.cbojar.reflacs.files.FilesCollector;
import net.cbojar.reflacs.files.FilesDistributor;
import net.cbojar.reflacs.ui.OnReady;
import net.cbojar.reflacs.ui.UI;
import net.cbojar.reflacs.ui.UIBuildTarget;

public final class CLI implements UI {
	private final Path sourceRoot;
	private final Path destinationRoot;
	private final OnReady onReady;

	private CLI(final Path sourceRoot, final Path destinationRoot, final OnReady onReady) {
		this.sourceRoot = sourceRoot;
		this.destinationRoot = destinationRoot;
		this.onReady = onReady;
	}

	public static UIBuildTarget target() {
		return new CLI.BuildTarget();
	}

	@Override
	public void run() throws IOException {
		onReady.ready(FilesCollector.from(sourceRoot), FilesDistributor.to(destinationRoot));
	}

	@Override
	public String toString() {
		return String.format("Source: %s, Destination: %s", sourceRoot, destinationRoot);
	}

	private static final class BuildTarget implements UIBuildTarget {
		@Override
		public UI build(final String[] args, final OnReady onReady) {
			final String sourceRoot = args[0];
			final String destinationRoot = args[1];

			return new CLI(Path.of(sourceRoot), Path.of(destinationRoot), onReady);
		}
	}
}
