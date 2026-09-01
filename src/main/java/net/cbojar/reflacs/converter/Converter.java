package net.cbojar.reflacs.converter;

import java.io.IOException;

import net.cbojar.reflacs.storage.Destination;
import net.cbojar.reflacs.storage.Source;

public interface Converter {
	public Destination convert(final Source source) throws IOException;

	public static Converter nullConverter() {
		return NullConverter.instance();
	}
}
