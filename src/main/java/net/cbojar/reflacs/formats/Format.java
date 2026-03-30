package net.cbojar.reflacs.formats;

import java.util.Optional;

public interface Format {
	String extension();
	String format();
	Optional<String> codec();
	Optional<String> quality();
	Optional<String> bitrate();
}
