package net.cbojar.reflacs.ui.gui;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import javax.swing.SwingUtilities;

final class JobManager implements AutoCloseable {
	private final ExecutorService executor;
	private final Consumer<Runnable> swingInvoke;

	private JobManager(final ExecutorService executor, final Consumer<Runnable> swingInvoke) {
		this.executor = executor;
		this.swingInvoke = swingInvoke;
	}

	@SuppressWarnings("resource")
	public static JobManager create() {
		final ExecutorService executor = Executors.newCachedThreadPool();
		return createFrom(executor, job -> SwingUtilities.invokeLater(job));
	}

	private static JobManager createFrom(final ExecutorService executor, final Consumer<Runnable> swingInvoke) {
		return new JobManager(executor, swingInvoke);
	}

	public void run(final Runnable job) {
		executor.execute(job);
	}

	public void runForUI(final Runnable job) {
		swingInvoke.accept(job);
	}

	@Override
	public void close() {
		executor.close();
	}
}
