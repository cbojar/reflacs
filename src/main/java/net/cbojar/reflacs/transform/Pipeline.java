package net.cbojar.reflacs.transform;

import java.io.IOException;
import java.nio.file.Path;
import net.cbojar.reflacs.formats.Format;

public final class Pipeline {
	private final Source source;
	private final Destination destination;
	private final Format format;
	private final Renamer renamer;
	private final Iterable<Filter> includeFilters;

	Pipeline(
			final Source source,
			final Destination destination,
			final Format format,
			final Renamer renamer,
			final Iterable<Filter> includeFilters) {
		this.source = source;
		this.destination = destination;
		this.format = format;
		this.renamer = renamer;
		this.includeFilters = includeFilters;
	}

	public static PipelineBuilder build() {
		return new PipelineBuilder();
	}

	public void transform(final Transform transform) throws IOException {
		for (final Input input : source.inputs()) {
			final Path outputName = rename(input.name());

			if (include(input.name(), outputName)) {
				final Bytes outputData = transform.transform(format, input.data());
				destination.write(Output.of(outputName, outputData));
			}
		}
	}

	private Path rename(final Path input) {
		return Path.of(String.format("%s.%s", renamer.rename(input), format.extension()));
	}

	private boolean include(final Path inputName, final Path outputName) throws IOException {
		final Filterables filterables = new Filterables(inputName, outputName, format);

		for (final Filter filter : includeFilters) {
			if (!filter.test(filterables)) {
				return false;
			}
		}

		return true;
	}
}
