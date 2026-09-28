package net.cbojar.reflacs.transform.files;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Collectors;

import net.cbojar.reflacs.transform.Renamer;

public final class WindowsRenamer implements Renamer {
	private static final WindowsRenamer INSTANCE = new WindowsRenamer();

	private WindowsRenamer() {
		// Prevent external instantiation
	}

	public static Renamer instance() {
		return INSTANCE;
	}

	@Override
	public Path rename(final Path path) {
		return Path.of(Arrays.stream(path.toString().split("/"))
			.map(part -> sanitizePart(part))
			.collect(Collectors.joining("/")));
	}

	private static String sanitizePart(final String part) {
		final StringBuilder cleanPart = new StringBuilder();

		for (int i = 0; i < part.length(); i++) {
			cleanPart.append(sanitizeChar(part.charAt(i)));
		}

		final char lastChar = cleanPart.charAt(cleanPart.length() - 1);
		if (lastChar == ' ' || lastChar == '.') {
			cleanPart.deleteCharAt(cleanPart.length() - 1);
			cleanPart.append('_');
		}

		return cleanPart.toString();
	}

	private static char sanitizeChar(final char c) {
		switch (c) {
			case '<':
			case '>':
			case ':':
			case '"':
			case '\\':
			case '|':
			case '?':
			case '*':
				return '_';
			default:
				return c;
		}
	}
}
