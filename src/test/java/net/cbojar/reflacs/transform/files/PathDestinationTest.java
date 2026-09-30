package net.cbojar.reflacs.transform.files;

import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;

import net.cbojar.reflacs.formats.Format;
import net.cbojar.reflacs.transform.Destination;
import net.cbojar.reflacs.transform.Output;

class PathDestinationTest {

	@Test
	public void shouldSeeAFileThatExists() {
		final PathStore store = new InMemoryPathStore()
			.write("/destination/exists.file", bytes(1, 2, 3));
		final Destination destination = PathDestination.to(store, Path.of("/destination"));

		final boolean exists = destination.exists(Path.of("/destination/exists.file"));

		assertThat(Boolean.valueOf(exists), is(Boolean.TRUE));
	}

	@Test
	public void shouldNotSeeAFileThatDoesNotExist() {
		final PathStore store = new InMemoryPathStore()
			.write("/destination/exists.file", bytes(1, 2, 3));
		final Destination destination = PathDestination.to(store, Path.of("/destination"));

		final boolean exists = destination.exists(Path.of("/destination/notexists.file"));

		assertThat(Boolean.valueOf(exists), is(Boolean.FALSE));
	}

	@Test
	public void shouldReadTheReflacsProperties() throws IOException {
		final PathStore store = new InMemoryPathStore()
			.writeProperties("/destination/.reflacs", properties -> {
				properties.setProperty("output-format", "ogg");
				properties.setProperty("bitrate", "123");
				properties.setProperty("quality", "9");
			});

		final Destination destination = PathDestination.to(store, Path.of("/destination"));

		final Format format = destination.readFormat();

		assertThat(format.format(), is("ogg"));
		assertThat(format.bitrate().get(), is("123"));
		assertThat(format.quality().get(), is("9"));
	}

	@Test
	public void shouldThrowWhenTheReflacsPropertiesAreMissing() {
		final PathStore store = new InMemoryPathStore();
		final Destination destination = PathDestination.to(store, Path.of("/destination"));

		assertThrows(IOException.class, () -> destination.readFormat());
	}

	@Test
	public void shouldWriteOutput() throws IOException {
		final InMemoryPathStore store = new InMemoryPathStore();
		final Destination destination = PathDestination.to(store, Path.of("/destination"));
		final Output output = Output.of(Path.of("output.file"), bytes(1, 2, 3));

		destination.write(output);
		final byte[] stored = store.readAllBytes("/destination/output.file");

		assertThat(stored, is(bytes(1, 2, 3)));
	}

	private static byte[] bytes(final int... values) {
		final byte[] bytes = new byte[values.length];
		for (int i = 0; i < values.length; i++) {
			bytes[i] = (byte)values[i];
		}
		return bytes;
	}
}
