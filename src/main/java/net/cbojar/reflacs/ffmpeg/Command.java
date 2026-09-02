package net.cbojar.reflacs.ffmpeg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.cbojar.reflacs.formats.Format;

final class Command {
	private final List<String> command;

	private Command(final List<String> command) {
		this.command = command;
	}

	public static Command build(final Format format) {
		final List<String> command = new ArrayList<>();

		addAllTo(command, "ffmpeg", "-i", "-", "-f", format.format());

		format.codec().ifPresent(c -> addAllTo(command, "-c:a", c));
		format.quality().ifPresent(q -> addAllTo(command, "-q:a", q));
		format.bitrate().ifPresent(b -> addAllTo(command, "-b:a", b));

		command.add("-");

		return new Command(command);
	}

	private static void addAllTo(final List<String> list, final String... values) {
		list.addAll(Arrays.asList(values));
	}

	public List<String> get() {
		return command;
	}

	@Override
	public String toString() {
		return String.join(" ", command);
	}
}
