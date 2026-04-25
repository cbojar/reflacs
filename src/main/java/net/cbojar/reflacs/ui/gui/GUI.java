package net.cbojar.reflacs.ui.gui;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import net.cbojar.reflacs.ui.UI;
import net.cbojar.reflacs.ui.UIBuildTarget;

public final class GUI implements UI {
	private final MainWindow window;
	private final JobManager jobs;
	private final CompletableFuture<Void> await;

	GUI(final MainWindow window, final JobManager jobs, final CompletableFuture<Void> await) {
		this.window = window;
		this.jobs = jobs;
		this.await = await;
	}

	public static UIBuildTarget target() {
		return new GUIBuildTarget();
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
