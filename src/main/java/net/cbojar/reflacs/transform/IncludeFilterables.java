package net.cbojar.reflacs.transform;

import java.nio.file.Path;

import net.cbojar.reflacs.formats.Format;

public final class IncludeFilterables {
	private final Path input;
	private final Path output;
	private final Format format;

	private IncludeFilterables(final Path input, final Path output, final Format format) {
		this.input = input;
		this.output = output;
		this.format = format;
	}

	public static IncludeFilterables of(final Path input, final Path output, final Format format) {
		return new IncludeFilterables(input, output, format);
	}

	public Path input() {
		return input;
	}

	public Path output() {
		return output;
	}

	public Format format() {
		return format;
	}
}
