package net.cbojar.reflacs.ffmpeg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

final class Command {
	private final List<String> command;

	private Command(final List<String> command) {
		this.command = command;
	}

	public static Command build(final FFMPEGConfiguration configuration) {
		return build(configuration.outputFormat(), configuration.additionalOptions());
	}

	public static Command build(final String format, final List<String> additionalOptions) {
		final List<String> command = new ArrayList<>(Arrays.asList("ffmpeg", "-i", "-", "-f", format));
		command.addAll(additionalOptions);
		command.add("-");
		return new Command(command);
	}

	public List<String> get() {
		return command;
	}

	@Override
	public String toString() {
		return String.join(" ", command);
	}
}
