package net.cbojar.reflacs.files;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

import net.cbojar.reflacs.formats.Format;
import net.cbojar.reflacs.formats.Formats;
import net.cbojar.reflacs.formats.Options;
import net.cbojar.reflacs.storage.Destination;
import net.cbojar.reflacs.storage.Distributor;

public final class FilesDistributor implements Distributor {
	private final Path root;
	private final Format format;

	private FilesDistributor(final Path root, final Format format) {
		this.root = root;
		this.format = format;
	}

	public static Distributor to(final Path destination) throws IOException {
		return new FilesDistributor(destination, Formats.withOptions(readOptions(destination)));
	}

	private static Options readOptions(final Path destination) throws IOException {
		final Path configurationFile = destination.resolve(".reflacs");

		if (!Files.exists(configurationFile)) {
			throw new IOException("Reflacs configuration file not found: " + configurationFile);
		}

		final Properties properties = new Properties();
		try (final BufferedReader configurationReader = Files.newBufferedReader(configurationFile)) {
			properties.load(configurationReader);
		}

		return Options.of(properties);
	}

	@Override
	public Format format() {
		return format;
	}

	@Override
	public void distribute(final Destination media) throws IOException {
		final Path destination = root.resolve(String.format("%s.%s", media.key(), format.extension()));

		Files.createDirectories(destination.getParent());
		try (OutputStream out = Files.newOutputStream(destination)) {
			media.writeTo(out);
		}
	}
}
