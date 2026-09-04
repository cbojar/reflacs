package net.cbojar.reflacs.transform;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.cbojar.reflacs.formats.Format;

public final class PipelineBuilder {
	private Source source = null;
	private Destination destination = null;
	private Format format = null;
	private Renamer renamer = Renamer.NOOP;
	private final List<IncludeFilter> includeFilters = new ArrayList<>();

	PipelineBuilder() {
		// Prevent public instantiation
	}

	public PipelineBuilder withSource(final Source newSource) {
		source = requireNonNull(newSource, "New source must not be null");
		return this;
	}

	public PipelineBuilder withDestination(final Destination newDestination) {
		destination = requireNonNull(newDestination, "New destination must not be null");
		return this;
	}

	public PipelineBuilder withFormat(final Format newFormat) {
		format = requireNonNull(newFormat, "New format must not be null");
		return this;
	}

	public PipelineBuilder withRenamer(final Renamer newRenamer) {
		renamer = requireNonNull(newRenamer, "New renamer must not be null");
		return this;
	}

	public PipelineBuilder addIncludeFilter(final IncludeFilter filter) {
		includeFilters.add(requireNonNull(filter, "Include filter must not be null"));
		return this;
	}

	public PipelineBuilder addIncludeFilters(final Collection<IncludeFilter> filters) {
		includeFilters.addAll(
			noneNull(
				requireNonNull(
					filters,
					"Include filters collection must not be null"),
				"Include filters collection must not contain null"));
		return this;
	}

	public PipelineBuilder withIncludeFilters(final Collection<IncludeFilter> filters) {
		includeFilters.clear();
		return addIncludeFilters(filters);
	}

	public Pipeline finish() {
		return new Pipeline(
			requireNonNull(source, "Source must be specified"),
			requireNonNull(destination, "Destination must be specified"),
			requireNonNull(format, "Format must be specified"),
			renamer,
			includeFilters);
	}

	private static <T> Collection<T> noneNull(final Collection<T> values, final String message) {
		assert values != null;

		if (values.stream().anyMatch(v -> v == null)) {
			throw new IllegalArgumentException(message);
		}

		return values;
	}
}
