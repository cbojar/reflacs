package net.cbojar.reflacs.transform;

import java.nio.file.Path;

import net.cbojar.reflacs.formats.Format;

public record Filterables(Path input, Path output, Format format) {
	// No additional implementation details
}
