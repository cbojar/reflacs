package net.cbojar.reflacs;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;

import net.cbojar.reflacs.ffmpeg.FFMPEG;
import net.cbojar.reflacs.storage.Source;
import net.cbojar.reflacs.ui.UI;
import net.cbojar.reflacs.ui.cli.CLI;
import net.cbojar.reflacs.ui.gui.GUI;

public final class Main {
	public static void main(final String... args) throws IOException {
		final UI<Path> ui = uiFrom(args);

		System.out.println(ui);

		ui.whenReady((collector, distributor) -> {
			final FFMPEG converter = FFMPEG.of(distributor.format());

			for (final Source<Path> flac : collector.collect()) {
				distributor.distribute(converter.convert(flac));
			}
		});
	}

	private static UI<Path> uiFrom(final String... args) throws IOException {
		if (args.length < 1) {
			throw new IOException("UI mode required as first argument");
		}

		final String arg = args[0];
		final String[] rest = Arrays.copyOfRange(args, 1, args.length);

		switch (arg) {
			case "cli":
				return CLI.fromArgs(rest);
			case "gui":
				return GUI.show();
			default:
				throw new IOException("Unknown UI mode: " + arg);
		}
	}
}
