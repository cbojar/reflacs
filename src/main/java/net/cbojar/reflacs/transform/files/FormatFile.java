package net.cbojar.reflacs.transform.files;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

import net.cbojar.reflacs.formats.Format;
import net.cbojar.reflacs.formats.Formats;
import net.cbojar.reflacs.formats.Options;

public final class FormatFile {
	private FormatFile() {
		// Prevent instantiation
	}

	public static Format readDirectory(final Path directory) throws IOException {
		return readFile(directory.resolve(".reflacs"));
	}

	public static Format readFile(final Path file) throws IOException {
		if (!Files.exists(file)) {
			throw new IOException("Reflacs configuration file not found: " + file);
		}

		final Properties properties = new Properties();
		try (final BufferedReader configurationReader = Files.newBufferedReader(file)) {
			properties.load(configurationReader);
		}

		return Formats.withOptions(Options.of(properties));
	}
}
