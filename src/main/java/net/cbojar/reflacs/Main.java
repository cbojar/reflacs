package net.cbojar.reflacs;

import java.io.IOException;
import java.nio.file.Path;

import net.cbojar.reflacs.ffmpeg.FFMPEG;
import net.cbojar.reflacs.files.FilesCollector;
import net.cbojar.reflacs.files.FilesDistributor;
import net.cbojar.reflacs.storage.Collector;
import net.cbojar.reflacs.storage.Distributor;
import net.cbojar.reflacs.storage.Media;

public final class Main {
	public static void main(final String... args) throws IOException {
		final String source = args[0];
		final String destination = args[1];

		System.out.printf("Source: %s, Distributor: %s%n", source, destination);

		final Collector<Path> collector = FilesCollector.create(source);
		final Distributor<Path> distributor = FilesDistributor.to(destination);
		final FFMPEG converter = FFMPEG.of(distributor.format());

		for (final Media<Path> flac : collector.collect()) {
			distributor.distribute(converter.convert(flac));
		}
	}
}
