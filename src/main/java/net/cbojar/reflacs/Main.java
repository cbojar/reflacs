package net.cbojar.reflacs;

import java.io.IOException;
import java.nio.file.Path;

import net.cbojar.reflacs.ffmpeg.FFMPEG;
import net.cbojar.reflacs.storage.Collector;
import net.cbojar.reflacs.storage.Distributor;
import net.cbojar.reflacs.storage.Source;
import net.cbojar.reflacs.ui.UI;
import net.cbojar.reflacs.ui.cli.CLI;

public final class Main {
	public static void main(final String... args) throws IOException {
		final UI<Path> ui = CLI.fromArgs(args);

		System.out.println(ui);

		final Collector<Path> collector = ui.collector();
		final Distributor<Path> distributor = ui.distributor();
		final FFMPEG converter = FFMPEG.of(distributor.format());

		for (final Source<Path> flac : collector.collect()) {
			distributor.distribute(converter.convert(flac));
		}
	}
}
