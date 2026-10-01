package net.cbojar.reflacs.transform;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;

import net.cbojar.reflacs.formats.Format;
import net.cbojar.reflacs.formats.Formats;
import net.cbojar.reflacs.formats.Options;

public final class Destination {
	private final PathStore store;
	private final Path root;

	private Destination(final PathStore store, final Path root) {
		this.store = store;
		this.root = root;
	}

	public static Destination to(final PathStore store, final Path destination) {
		return new Destination(store, destination);
	}

	public Format readFormat() throws IOException {
		final Path file = root.resolve(".reflacs");

		if (!store.exists(file)) {
			throw new IOException("Reflacs configuration file not found: " + file);
		}

		return Formats.withOptions(Options.of(store.readProperties(file)));
	}

	public boolean exists(final Path path) {
		return store.exists(root.resolve(path));
	}

	public void write(final Output output) throws IOException {
		final Path destination = root.resolve(output.name());

		try (OutputStream out = store.outputStreamTo(destination)) {
			output.data().writeTo(out);
		}
	}
}
