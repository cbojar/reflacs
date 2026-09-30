package net.cbojar.reflacs.transform.files;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import net.cbojar.reflacs.formats.Format;
import net.cbojar.reflacs.formats.Formats;
import net.cbojar.reflacs.formats.Options;
import net.cbojar.reflacs.transform.Destination;
import net.cbojar.reflacs.transform.Output;

public final class PathDestination implements Destination {
	private final PathStore store;
	private final Path root;

	private PathDestination(final PathStore store, final Path root) {
		this.store = store;
		this.root = root;
	}

	public static Destination to(final PathStore store, final Path destination) {
		return new PathDestination(store, destination);
	}

	@Override
	public Format readFormat() throws IOException {
		final Path file = root.resolve(".reflacs");

		if (!store.exists(file)) {
			throw new IOException("Reflacs configuration file not found: " + file);
		}

		return Formats.withOptions(Options.of(store.readProperties(file)));
	}

	@Override
	public boolean exists(final Path path) {
		return store.exists(root.resolve(path));
	}

	@Override
	public void write(final Output output) throws IOException {
		final Path destination = root.resolve(output.name());

		try (OutputStream out = store.outputStreamTo(destination)) {
			output.data().writeTo(out);
		}
	}
}
