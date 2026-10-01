package net.cbojar.reflacs.transform;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.List;
import java.util.Properties;

public interface PathStore {
	boolean exists(final Path path);
	List<Path> findFlacFiles(final Path start) throws IOException;
	OutputStream outputStreamTo(final Path path) throws IOException;
	Properties readProperties(final Path file) throws IOException;
	byte[] readAllBytes(final Path path) throws IOException;
}
