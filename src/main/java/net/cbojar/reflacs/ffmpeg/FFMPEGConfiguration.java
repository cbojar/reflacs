package net.cbojar.reflacs.ffmpeg;

import java.util.Arrays;
import java.util.List;

public final class FFMPEGConfiguration {
	public String outputFormat() {
		return "ogg";
	}

	public List<String> additionalOptions() {
		return Arrays.asList("-b:a", "256K");
	}
}
