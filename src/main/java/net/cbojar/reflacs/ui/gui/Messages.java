package net.cbojar.reflacs.ui.gui;

import java.awt.Component;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

final class Messages {
	private static final DateTimeFormatter TIMESTAMP_FORMAT =
		DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT, FormatStyle.MEDIUM);

	private final JobManager manager;
	private final JScrollPane scrollPane;
	private final JTextArea textArea;

	private Messages(final JobManager manager, final JScrollPane scrollPane, final JTextArea textArea) {
		this.manager = manager;
		this.scrollPane = scrollPane;
		this.textArea = textArea;
	}

	public static Messages create(final JobManager manager) {
		final JTextArea textArea = new JTextArea();
		final JScrollPane scrollPane = new JScrollPane(textArea);
		return new Messages(manager, scrollPane, textArea);
	}

	public void showInfo(final String title, final String text) {
		showMessage("INFO", title, text);
	}

	public void showWarning(final String title, final String text) {
		showMessage("WARNING", title, text);
	}

	public void showError(final String title, final String text) {
		showMessage("ERROR", title, text);
	}

	public void showError(final String title, final String text, final Throwable exception) {
		final ByteArrayOutputStream traceBytes = new ByteArrayOutputStream();
		final PrintStream traceStream = new PrintStream(traceBytes);
		exception.printStackTrace(traceStream);
		final String trace = traceBytes.toString(StandardCharsets.UTF_8);
		if (text.isBlank()) {
			showMessage("ERROR", title, trace);
		} else {
			showMessage("ERROR", title, String.format("%s%n%s", text, trace));
		}
	}

	public void showError(final String title, final Throwable exception) {
		showError(title, "", exception);
	}

	public void showError(final Throwable exception) {
		showError(exception.getMessage(), exception);
	}

	private void showMessage(final String level, final String title, final String text) {
		final String leadingNewline = textArea.getText().isEmpty() ? "" : "\n";
		final String now = LocalDateTime.now().format(TIMESTAMP_FORMAT);
		final String message = String.format("%s[%s::%s] %s: %s", leadingNewline, level, now, title, text);

		manager.runForUI(() -> textArea.append(message));
	}

	public <T extends Throwable> void reportErrors(final CanThrow<T> canThrow) {
		try {
			canThrow.run();
		} catch (final Throwable ex) {
			showError(ex);
		}
	}

	public Component asComponent() {
		return scrollPane;
	}
}
