package net.cbojar.reflacs.ui.gui;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import net.cbojar.reflacs.transform.Transform;
import net.cbojar.reflacs.ui.UI;

public final class GUI implements UI {
	private final MainWindow window;
	private final JobManager jobs;
	private final CompletableFuture<Void> await;

	GUI(final MainWindow window, final JobManager jobs, final CompletableFuture<Void> await) {
		this.window = window;
		this.jobs = jobs;
		this.await = await;
	}

	@SuppressWarnings("resource")
	public static UI build(final Transform transform) {
		final JobManager jobs = JobManager.create();
		final CompletableFuture<Void> await = new CompletableFuture<>();

		final MainWindow window = MainWindow.create(jobs, transform)
			.addCloseListener(event -> await.complete(null))
			.pack();

		return new GUI(window, jobs, await);
	}

	@Override
	public void run() throws IOException {
		jobs.runForUI(() -> window.setVisible(true));
		await();
	}

	private void await() throws IOException {
		try {
			await.get();
		} catch (final InterruptedException | ExecutionException ex) {
			throw new IOException(ex);
		}
	}

	@Override
	public void close() {
		jobs.close();
	}
}
