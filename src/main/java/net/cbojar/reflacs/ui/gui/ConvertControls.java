package net.cbojar.reflacs.ui.gui;

import java.awt.Component;
import javax.swing.JButton;

final class ConvertControls {
	private final JobManager jobs;
	private final JButton button;

	private ConvertControls(final JobManager jobs, final JButton button) {
		this.jobs = jobs;
		this.button = button;
	}

	public static ConvertControls create(final JobManager jobs) {
		final JButton convertButton = new JButton("Convert");
		return new ConvertControls(jobs, convertButton);
	}

	public ConvertControls addConvertListener(final Runnable listener) {
		button.addActionListener(event -> {
			jobs.runForUI(() -> button.setEnabled(false));
			jobs.run(() -> {
				listener.run();
				jobs.runForUI(() -> button.setEnabled(true));
			});
		});

		return this;
	}

	public Component asComponent() {
		return button;
	}
}
