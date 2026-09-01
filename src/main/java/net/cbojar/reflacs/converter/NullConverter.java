package net.cbojar.reflacs.converter;

import java.io.IOException;

import net.cbojar.reflacs.storage.Destination;
import net.cbojar.reflacs.storage.Source;

public final class NullConverter implements Converter {
	private static final NullConverter INSTANCE = new NullConverter();

	private NullConverter() {
		// prevent external instantiation
	}

	static Converter instance() {
		return INSTANCE;
	}

	@Override
	public Destination convert(final Source source) throws IOException {
		return Destination.of(source.key(), new byte[0]);
	}
}
