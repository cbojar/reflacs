package net.cbojar.reflacs;

import java.io.IOException;
import java.util.List;

import net.cbojar.reflacs.ffmpeg.FFMPEG;
import net.cbojar.reflacs.ui.UI;
import net.cbojar.reflacs.ui.cli.CLI;
import net.cbojar.reflacs.ui.gui.GUI;

public final class Main {
	public static void main(final String... args) throws IOException {
		try (UI ui = uiFrom(List.of(args))) {
			ui.run();
		}
	}

	private static UI uiFrom(final List<String> args) throws IOException {
		if (args.isEmpty()) {
			throw new IOException("UI mode required as first argument");
		}

		final String uiMode = args.get(0);
		final FFMPEG transform = FFMPEG.instance();

		switch (uiMode) {
			case "cli":
				return CLI.build(transform, args.subList(1, args.size()));
			case "gui":
				return GUI.build(transform);
			default:
				throw new IOException("Unknown UI mode: " + uiMode);
		}
	}
}
