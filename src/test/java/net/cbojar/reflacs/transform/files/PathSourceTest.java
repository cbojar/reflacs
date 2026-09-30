package net.cbojar.reflacs.transform.files;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.StreamSupport;

import net.cbojar.reflacs.transform.Bytes;
import net.cbojar.reflacs.transform.Input;
import net.cbojar.reflacs.transform.Source;

import org.junit.jupiter.api.Test;

class PathSourceTest {
	@Test
	public void shouldReadListOfFlacFiles() throws IOException {
		final PathStore store = new InMemoryPathStore()
			.write("/source/flac1.flac", bytes(1, 2, 3))
			.write("/source/flac2.flac", bytes(2, 3, 4))
			.write("/source/notflac.txt", bytes(3, 4, 5))
			.write("/elsewhere/flac3.flac", bytes(4, 5, 6));
		final Source source = PathSource.from(store, Path.of("/source"));

		final List<Input> inputs = toList(source.inputs());

		assertThat(Integer.valueOf(inputs.size()), is(Integer.valueOf(2)));

		assertThat(inputs.get(0).name(), is(Path.of("flac1")));
		assertThat(extract(inputs.get(0).data()), is(bytes(1, 2, 3)));

		assertThat(inputs.get(1).name(), is(Path.of("flac2")));
		assertThat(extract(inputs.get(1).data()), is(bytes(2, 3, 4)));
	}

	private static byte[] bytes(final int... values) {
		final byte[] bytes = new byte[values.length];
		for (int i = 0; i < values.length; i++) {
			bytes[i] = (byte)values[i];
		}
		return bytes;
	}

	private static <T> List<T> toList(final Iterable<T> iterable) {
		return StreamSupport.stream(iterable.spliterator(), false).toList();
	}

	private static byte[] extract(final Bytes bytes) throws IOException {
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		bytes.writeTo(out);
		return out.toByteArray();
	}
}
