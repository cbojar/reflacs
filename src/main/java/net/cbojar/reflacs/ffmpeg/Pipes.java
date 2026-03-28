package net.cbojar.reflacs.ffmpeg;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

final class Pipes implements AutoCloseable {
	private final ExecutorService executor = Executors.newFixedThreadPool(2);
	private final List<Future<IOException>> futures = new ArrayList<>();
	private final Process ffmpeg;

	private Pipes(final Process ffmpeg) {
		this.ffmpeg = ffmpeg;
	}

	public static Pipes createFor(final Process ffmpeg) {
		return new Pipes(ffmpeg);
	}

	public void pipeIn(final PipeIn pipeIn) {
		futures.add(executor.submit(new PipeInCallable(ffmpeg, pipeIn)));
	}

	public void pipeOut(final PipeOut pipeOut) {
		futures.add(executor.submit(new PipeOutCallable(ffmpeg, pipeOut)));
	}

	public void check() throws IOException {
		final IOException ex = futures.stream()
			.map(Pipes::extractFuture)
			.filter(Objects::nonNull)
			.findFirst()
			.orElse(null);

		if (ex != null) {
			throw ex;
		}
	}

	private static IOException extractFuture(final Future<IOException> future) {
		try {
			return future.get();
		} catch (final InterruptedException | ExecutionException ex) {
			return new IOException(ex);
		}
	}

	@Override
	public void close() {
		executor.close();
	}

	private static class PipeInCallable implements Callable<IOException> {
		private final Process ffmpeg;
		private final PipeIn pipeIn;

		public PipeInCallable(final Process ffmpeg, final PipeIn pipeIn) {
			this.ffmpeg = ffmpeg;
			this.pipeIn = pipeIn;
		}

		@Override
		public IOException call() throws Exception {
			try (final OutputStream outputStream = ffmpeg.getOutputStream()) {
				pipeIn.pipe(outputStream);
				return null;
			} catch (final IOException ex) {
				return ex;
			}
		}
	}

	private static class PipeOutCallable implements Callable<IOException> {
		private final Process ffmpeg;
		private final PipeOut pipeOut;

		public PipeOutCallable(final Process ffmpeg, final PipeOut pipeOut) {
			this.ffmpeg = ffmpeg;
			this.pipeOut = pipeOut;
		}
		@Override
		public IOException call() throws Exception {
			try (final InputStream inputStream = ffmpeg.getInputStream()) {
				pipeOut.pipe(inputStream);
				return null;
			} catch (final IOException ex) {
				return ex;
			}
		}
	}
}
