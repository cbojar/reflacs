package net.cbojar.reflacs.ui.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.function.Consumer;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import net.cbojar.reflacs.formats.Format;
import net.cbojar.reflacs.transform.Destination;
import net.cbojar.reflacs.transform.Pipeline;
import net.cbojar.reflacs.transform.Source;
import net.cbojar.reflacs.transform.Transform;
import net.cbojar.reflacs.transform.files.PathDestination;
import net.cbojar.reflacs.transform.files.PathSource;

final class MainWindow {
	private final JFrame window;

	private MainWindow(final JFrame window) {
		this.window = window;
	}

	public static MainWindow create(final JobManager jobs, final Transform transform) {
		final JFrame window = new JFrame();
		window.setTitle("reflacs");
		window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		window.setMinimumSize(new Dimension(800, 600));
		window.setLayout(new BorderLayout());

		final Messages messages = Messages.create(jobs);

		final Paths paths = Paths.create();
		final ConvertOptions convertOptions = ConvertOptions.create();

		final FileSelector selector = FileSelector.create(jobs, messages)
			.addSourcePathChangedListener(paths::updateSource)
			.addDestinationPathChangedListener(paths::updateDestination);

		final ConvertControls convert = ConvertControls.create(jobs, convertOptions)
			.addConvertListener(runTransform(messages, paths, transform));

		window.add(messages.asComponent(), BorderLayout.NORTH);
		window.add(selector.asComponent(), BorderLayout.CENTER);
		window.add(convert.asComponent(), BorderLayout.SOUTH);

		return new MainWindow(window)
			.addOpenListener(event -> selector.ensureLayout());
	}

	private static Consumer<ConvertOptions> runTransform(final Messages messages, final Paths paths, final Transform transform) {
		return options ->
			messages.reportErrors(() -> {
				final Source source = PathSource.from(paths.source());
				final Destination destination = PathDestination.to(paths.destination());
				final Format format = destination.readFormat();

				Pipeline.build()
					.withSource(source)
					.withDestination(destination)
					.withFormat(format)
					.finish()
					.transform(transform);
			});
	}

	public MainWindow addOpenListener(final Consumer<WindowEvent> listener) {
		window.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(final WindowEvent event) {
				listener.accept(event);
			}
		});

		return this;
	}

	public MainWindow addCloseListener(final Consumer<WindowEvent> listener) {
		window.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(final WindowEvent event) {
				listener.accept(event);
			}
		});

		return this;
	}

	public MainWindow pack() {
		window.pack();
		return this;
	}

	public void setVisible(final boolean visible) {
		window.setVisible(visible);
	}
}
