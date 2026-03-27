package net.cbojar.reflacs.ffmpeg;

import net.cbojar.reflacs.configuration.DestinationConfiguration;
import net.cbojar.reflacs.media.Converted;
import net.cbojar.reflacs.media.Converter;
import net.cbojar.reflacs.media.Flac;

public final class FFMPEG implements Converter {
	private final DestinationConfiguration configuration;

	private FFMPEG(final DestinationConfiguration configuration) {
		this.configuration = configuration;
	}

	public static Converter converter(final DestinationConfiguration configuration) {
		return new FFMPEG(configuration);
	}

	@Override
	public Converted convert(final Flac flac) {
		return Converted.of(flac.name());
	}
}
