package net.cbojar.reflacs.transform;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import net.cbojar.reflacs.formats.Format;

public final class PipelineBuilder {
	private Source source = null;
	private Destination destination = null;
	private Format format = null;
	private Renamer renamer = Renamer.NOOP;
	private final List<Filter> includeFilters = new ArrayList<>();

	PipelineBuilder() {
		// Prevent public instantiation
	}

	public PipelineBuilder source(final Source newSource) {
		source = requireNonNull(newSource, "New source must not be null");
		return this;
	}

	public PipelineBuilder destination(final Destination newDestination) {
		destination = requireNonNull(newDestination, "New destination must not be null");
		return this;
	}

	public PipelineBuilder format(final Format newFormat) {
		format = requireNonNull(newFormat, "New format must not be null");
		return this;
	}

	public PipelineBuilder renamer(final Renamer newRenamer) {
		renamer = requireNonNull(newRenamer, "New renamer must not be null");
		return this;
	}

	public PipelineBuilder include(final Filter filter) {
		includeFilters.add(requireNonNull(filter, "Include filter must not be null"));
		return this;
	}

	public PipelineBuilder includeIf(final boolean add, final Filter filter) {
		return add ? include(filter) : this;
	}

	public PipelineBuilder exclude(final Filter filter) {
		includeFilters.add(requireNonNull(filter, "Exclude filter must not be null").negate());
		return this;
	}

	public PipelineBuilder excludeIf(final boolean add, final Filter filter) {
		return add ? exclude(filter) : this;
	}

	public Pipeline finish() {
		return new Pipeline(
			requireNonNull(source, "Source must be specified"),
			requireNonNull(destination, "Destination must be specified"),
			requireNonNull(format, "Format must be specified"),
			renamer,
			includeFilters);
	}
}
