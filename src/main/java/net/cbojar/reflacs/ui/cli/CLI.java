package net.cbojar.reflacs.ui.cli;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import net.cbojar.reflacs.formats.Format;
import net.cbojar.reflacs.transform.Destination;
import net.cbojar.reflacs.transform.Pipeline;
import net.cbojar.reflacs.transform.Source;
import net.cbojar.reflacs.transform.Transform;
import net.cbojar.reflacs.transform.files.PathDestination;
import net.cbojar.reflacs.transform.files.PathSource;
import net.cbojar.reflacs.ui.UI;

public final class CLI implements UI {
	private final Path sourceRoot;
	private final Path destinationRoot;
	private final Transform transform;

	private CLI(final Path sourceRoot, final Path destinationRoot, final Transform transform) {
		this.sourceRoot = sourceRoot;
		this.destinationRoot = destinationRoot;
		this.transform = transform;
	}

	public static CLI build(final Transform transform, final List<String> args) {
		final String sourceRoot = args.get(0);
		final String destinationRoot = args.get(1);

		return build(transform, sourceRoot, destinationRoot);
	}

	public static CLI build(final Transform transform, final String sourceRoot, final String destinationRoot) {
		return new CLI(Path.of(sourceRoot), Path.of(destinationRoot), transform);
	}

	@Override
	public void run() throws IOException {
		final Source source = PathSource.from(sourceRoot);
		final Destination destination = PathDestination.to(destinationRoot);
		final Format format = destination.readFormat();

		Pipeline.build()
			.withSource(source)
			.withDestination(destination)
			.withFormat(format)
			.finish()
			.transform(transform);
	}

	@Override
	public void close() {
		// Do nothing
	}
}
