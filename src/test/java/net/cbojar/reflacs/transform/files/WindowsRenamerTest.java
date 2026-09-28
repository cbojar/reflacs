package net.cbojar.reflacs.transform.files;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;

class WindowsRenamerTest {
	@Test
	public void shouldDoNothingToAValidPath() {
		final Path valid = Path.of("A/Valid/Path.flac");

		final Path result = WindowsRenamer.instance().rename(valid);

		assertThat(result, is(valid));
	}

	@Test
	public void shouldReplaceInvalidCharactersInTheFileName() {
		final Path invalid = Path.of("An/Invalid/File<x>x:x\"x\\x|x?x*Path.flac");
		final Path valid = Path.of("An/Invalid/File_x_x_x_x_x_x_x_Path.flac");

		final Path result = WindowsRenamer.instance().rename(invalid);

		assertThat(result, is(valid));
	}

	@Test
	public void shouldReplaceInvalidCharactersInAnyPartOfThePath() {
		final Path invalid = Path.of("A<x>x:x\"x\\x|x?x*n/In<x>x:x\"x\\x|x?x*valid/File<x>x:x\"x\\x|x?x*Path.flac");
		final Path valid = Path.of("A_x_x_x_x_x_x_x_n/In_x_x_x_x_x_x_x_valid/File_x_x_x_x_x_x_x_Path.flac");

		final Path result = WindowsRenamer.instance().rename(invalid);

		assertThat(result, is(valid));
	}

	@Test
	public void shouldReplaceInvalidEndSpaceInTheFilename() {
		final Path invalid = Path.of("An/Invalid/FilePath ");
		final Path valid = Path.of("An/Invalid/FilePath_");

		final Path result = WindowsRenamer.instance().rename(invalid);

		assertThat(result, is(valid));
	}

	@Test
	public void shouldReplaceInvalidEndSpaceInAnyPartOfThePath() {
		final Path invalid = Path.of("An /Invalid /FilePath ");
		final Path valid = Path.of("An_/Invalid_/FilePath_");

		final Path result = WindowsRenamer.instance().rename(invalid);

		assertThat(result, is(valid));
	}

	@Test
	public void shouldReplaceInvalidEndDotInTheFilename() {
		final Path invalid = Path.of("An/Invalid/FilePath.");
		final Path valid = Path.of("An/Invalid/FilePath_");

		final Path result = WindowsRenamer.instance().rename(invalid);

		assertThat(result, is(valid));
	}

	@Test
	public void shouldReplaceInvalidEndDotInAnyPartOfThePath() {
		final Path invalid = Path.of("An./Invalid./FilePath.");
		final Path valid = Path.of("An_/Invalid_/FilePath_");

		final Path result = WindowsRenamer.instance().rename(invalid);

		assertThat(result, is(valid));
	}
}
