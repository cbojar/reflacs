package net.cbojar.reflacs.ffmpeg;

import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;

final class Run {
	private final Process ffmpeg;
	private final Command command;

	private Run(final Process ffmpeg, final Command command) {
		this.ffmpeg = ffmpeg;
		this.command = command;
	}

	public static Run start(final Command command) throws IOException {
		final Process ffmpeg = new ProcessBuilder(command.get())
			.redirectError(Redirect.INHERIT)
			.start();

		return new Run(ffmpeg, command);
	}

	public void withBlock(final RunBlock block) throws IOException {
		try (Pipes pipes = Pipes.createFor(ffmpeg)) {
			block.execute(pipes);
			await();
			pipes.check();
		}
	}

	public void await() throws IOException {
		try {
			final int exitCode = ffmpeg.waitFor();
			if (exitCode != 0) {
				throw new IOException(String.format("\"%s\" exited with non-zero code: %d",
						commandLine(), Integer.valueOf(exitCode)));
			}
		} catch (final InterruptedException ex) {
			throw new IOException(String.format("\"%s\" interrupted during conversion", commandLine()), ex);
		}
	}

	private String commandLine() {
		return ffmpeg.info().commandLine().orElseGet(() -> command.toString());
	}
}
