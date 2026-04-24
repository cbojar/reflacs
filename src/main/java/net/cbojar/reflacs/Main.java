package net.cbojar.reflacs;

import java.io.IOException;
import java.util.Arrays;

import net.cbojar.reflacs.ffmpeg.FFMPEG;
import net.cbojar.reflacs.storage.Collector;
import net.cbojar.reflacs.storage.Distributor;
import net.cbojar.reflacs.storage.Source;
import net.cbojar.reflacs.ui.OnReady;
import net.cbojar.reflacs.ui.UI;
import net.cbojar.reflacs.ui.UIBuilder;
import net.cbojar.reflacs.ui.cli.CLI;
import net.cbojar.reflacs.ui.gui.GUI;

public final class Main {
	public static void main(final String... args) throws IOException {
		try (UI ui = uiFrom(args, Main::ready)) {
			ui.run();
		}
	}

	private static void ready(final Collector collector, final Distributor distributor) throws IOException {
		final FFMPEG converter = FFMPEG.of(distributor.format());

		for (final Source flac : collector.collect()) {
			distributor.distribute(converter.convert(flac));
		}
	}

	private static UI uiFrom(final String[] args, final OnReady onReady) throws IOException {
		if (args.length < 1) {
			throw new IOException("UI mode required as first argument");
		}

		final String arg = args[0];
		final String[] rest = Arrays.copyOfRange(args, 1, args.length);
		final UIBuilder builder = UI.build().args(rest).onReady(onReady);

		switch (arg) {
			case "cli":
				return builder.buildInto(CLI.target());
			case "gui":
				return builder.buildInto(GUI.target());
			default:
				throw new IOException("Unknown UI mode: " + arg);
		}
	}
}
