package net.cbojar.reflacs.transform;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

final class Renamers implements Renamer {
	private final List<Renamer> renamers;

	private Renamers(final List<Renamer> renamers) {
		this.renamers = renamers;
	}

	public static Renamers of(final List<? extends Renamer> renamers) {
		return new Renamers(new ArrayList<>(renamers));
	}

	public static Renamers of(final Renamer... renamers) {
		return new Renamers(new ArrayList<>(List.of(renamers)));
	}

	public static Renamers create() {
		return new Renamers(new ArrayList<>());
	}

	public void add(final Renamer renamer) {
		renamers.add(renamer);
	}

	@Override
	public Path rename(final Path path) {
		Path renamed = path;

		for (final Renamer renamer : renamers) {
			renamed = renamer.rename(renamed);
		}

		return renamed;
	}
}
