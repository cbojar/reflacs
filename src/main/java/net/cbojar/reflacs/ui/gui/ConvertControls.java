package net.cbojar.reflacs.ui.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

final class ConvertControls {
	private final List<Consumer<ConvertOptions>> listeners;
	private final JPanel controls;

	private ConvertControls(final List<Consumer<ConvertOptions>> listeners, final JPanel controls) {
		this.listeners = listeners;
		this.controls = controls;
	}

	public static ConvertControls create(final JobManager jobs, final ConvertOptions options) {
		final JPanel panel = new JPanel(new BorderLayout());
		final JPanel checkboxes = new JPanel(new FlowLayout());
		final JCheckBox overwriteExisting = new JCheckBox("Overwrite exisitng", options.overwrite());
		final JCheckBox safeNames = new JCheckBox("Safe names", options.safeNames());
		final JButton button = new JButton("Convert");

		checkboxes.add(overwriteExisting);
		checkboxes.add(safeNames);
		panel.add(checkboxes, BorderLayout.CENTER);
		panel.add(button, BorderLayout.SOUTH);

		overwriteExisting.addItemListener(event -> options.overwrite(checked(event)));
		safeNames.addItemListener(event -> options.safeNames(checked(event)));

		final List<Consumer<ConvertOptions>> convertListeners = new ArrayList<>();

		button.addActionListener(event -> {
			jobs.runForUI(() -> button.setEnabled(false));
			jobs.run(() -> {
				convertListeners.forEach(l -> l.accept(options));
				jobs.runForUI(() -> button.setEnabled(true));
			});
		});

		return new ConvertControls(convertListeners, panel);
	}

	private static boolean checked(final ItemEvent event) {
		return event.getStateChange() == ItemEvent.SELECTED;
	}

	public ConvertControls addConvertListener(final Consumer<ConvertOptions> listener) {
		listeners.add(listener);
		return this;
	}

	public Component asComponent() {
		return controls;
	}
}
