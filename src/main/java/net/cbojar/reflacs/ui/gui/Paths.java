package net.cbojar.reflacs.ui.gui;

import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicReference;

final class Paths {
	private final AtomicReference<Path> source = new AtomicReference<>();
	private final AtomicReference<Path> destination = new AtomicReference<>();

	private Paths() {
		// Prevent external instantiation
	}

	public static Paths create() {
		return new Paths();
	}

	public Path source() {
		return source.get();
	}

	public void updateSource(final Path newPath) {
		source.set(newPath);
	}

	public Path destination() {
		return destination.get();
	}

	public void updateDestination(final Path newPath) {
		destination.set(newPath);
	}
}
