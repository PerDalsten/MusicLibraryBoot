package dk.purplegreen.musiclibraryboot.service;

public class ArtistNotFoundException extends MusicLibraryServiceException {

	private static final long serialVersionUID = 6511452772509671451L;

	public ArtistNotFoundException() {
		super();
	}

	public ArtistNotFoundException(String message) {
		super(message);
	}
}
