package net.cbojar.reflacs.transform.files;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import net.cbojar.reflacs.transform.Destination;
import net.cbojar.reflacs.transform.Output;

public final class PathDestination implements Destination {
	private final Path root;

	private PathDestination(final Path root) {
		this.root = root;
	}

	public static Destination to(final Path destination) {
		return new PathDestination(destination);
	}

	@Override
	public void write(final Output output) throws IOException {
		final Path destination = root.resolve(output.name());

		Files.createDirectories(destination.getParent());
		try (OutputStream out = Files.newOutputStream(destination)) {
			output.data().writeTo(out);
		}
	}
}
