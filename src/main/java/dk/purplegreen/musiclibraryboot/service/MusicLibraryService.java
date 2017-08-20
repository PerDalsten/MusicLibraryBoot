package dk.purplegreen.musiclibraryboot.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dk.purplegreen.musiclibraryboot.domain.Album;
import dk.purplegreen.musiclibraryboot.domain.Artist;
import dk.purplegreen.musiclibraryboot.repository.AlbumRepository;
import dk.purplegreen.musiclibraryboot.repository.ArtistRepository;

@Service
public class MusicLibraryService {

	private static final Logger log = LoggerFactory.getLogger(MusicLibraryService.class);

	private final ArtistRepository artistRepository;
	private final AlbumRepository albumRepository;

	@Autowired
	private ArtistValidator artistValidator;

	@Autowired
	private AlbumValidator albumValidator;

	@Autowired
	public MusicLibraryService(ArtistRepository artistRepository, AlbumRepository albumRepository) {
		this.artistRepository = artistRepository;
		this.albumRepository = albumRepository;
	}

	@Transactional(readOnly = true)
	public List<Artist> getArtists() {
		if (log.isDebugEnabled()) {
			log.debug("getArtists() called");
		}
		return artistRepository.findAllByOrderByNameAsc();
	}

	@Transactional(readOnly = true)
	public Artist getArtist(Integer id) throws MusicLibraryServiceException {
		if (log.isDebugEnabled()) {
			log.debug("getArtist() called with id: " + id);
		}

		Artist result = artistRepository.findOne(id);
		if (result == null)
			throw new ArtistNotFoundException("Artist with id: " + id + " does not exist.");
		return result;
	}

	@Transactional
	public Artist createArtist(Artist artist) throws MusicLibraryServiceException {

		if (artist == null) {
			throw new InvalidArtistException("Artist cannot be null");
		}

		if (log.isDebugEnabled()) {
			log.debug("createArtist called with artist: " + artist.getName());
		}

		artist.setId(null);
		artistValidator.validate(artist);
		return artistRepository.save(artist);
	}

	@Transactional
	public Artist updateArtist(Artist artist) throws MusicLibraryServiceException {

		if (artist == null) {
			throw new InvalidArtistException("Artist cannot be null");
		}

		if (log.isDebugEnabled()) {
			log.debug("updateArtist called with artist: " + artist.getId() + "-" + artist.getName());
		}

		getArtist(artist.getId());
		artistValidator.validate(artist);
		return artistRepository.save(artist);
	}

	@Transactional
	public void deleteArtist(Artist artist) throws MusicLibraryServiceException {

		artist = getArtist(artist.getId());

		if (albumRepository.countByArtist(artist) > 0) {
			throw new InvalidArtistException("Artist has albums and cannot be deleted");
		}

		if (log.isDebugEnabled()) {
			log.debug("deleteArtist() called with id: " + artist.getId());
		}
		artistRepository.delete(artist);
	}

	@Transactional(readOnly = true)
	public List<Album> getAlbums(Artist artist) throws MusicLibraryServiceException {

		artist = getArtist(artist.getId());

		if (log.isDebugEnabled()) {
			log.debug("getAlbums() called for artist: " + artist.getName());
		}
		return albumRepository.findByArtist(artist);
	}

	@Transactional(readOnly = true)
	public List<Album> findAlbums(String artist, String title, Integer year) {

		if (log.isDebugEnabled()) {
			log.debug("findAlbums called: artist=" + artist + ", title=" + title + ", year=" + year);
		}

		List<Album> result;

		if (artist == null && title == null && year == null) {
			result = albumRepository.findAllByOrderByTitleAsc();
		} else {
			result = albumRepository.findAll(new AlbumRepository.AlbumSpecification(artist, title, year));
		}

		return result;
	}

	@Transactional(readOnly = true)
	public Album getAlbum(Integer id) throws MusicLibraryServiceException {

		if (log.isDebugEnabled()) {
			log.debug("getAlbum() called with id: " + id);
		}

		Album result = albumRepository.findOne(id);
		if (result == null)
			throw new AlbumNotFoundException("Album with id: " + id + " does not exist.");
		return result;
	}

	@Transactional(readOnly = true)
	public List<Album> getAlbums() {
		if (log.isDebugEnabled()) {
			log.debug("getAlbums() called");
		}

		return albumRepository.findAllByOrderByTitleAsc();
	}

	@Transactional
	public Album createAlbum(Album album) throws MusicLibraryServiceException {

		if (album == null) {
			throw new InvalidAlbumException("Album cannot be null");
		}

		if (log.isDebugEnabled()) {
			log.debug("createAlbum called with artist: " + album.getTitle());
		}

		album.setId(null);
		attachSongs(album);
		albumValidator.validate(album);
		return albumRepository.save(album);
	}

	@Transactional
	public Album updateAlbum(Album album) throws MusicLibraryServiceException {

		if (album == null) {
			throw new InvalidAlbumException("Album cannot be null");
		}

		if (log.isDebugEnabled()) {
			log.debug("updateAlbum called with album: " + album.getId() + "-" + album.getTitle());
		}

		getAlbum(album.getId());
		attachSongs(album);
		albumValidator.validate(album);
		return albumRepository.save(album);
	}

	@Transactional
	public void deleteAlbum(Album album) throws MusicLibraryServiceException {
		if (log.isDebugEnabled()) {
			log.debug("deleteAlbum() called with id: " + album.getId());
		}
		albumRepository.delete(getAlbum(album.getId()));
	}

	private void attachSongs(Album album) {
		album.getSongs().stream().forEach(song -> song.setAlbum(album));			
	}
}
