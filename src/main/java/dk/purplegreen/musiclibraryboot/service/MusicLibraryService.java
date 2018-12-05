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

		log.debug("getArtists() called");

		return artistRepository.findAllByOrderByNameAsc();
	}

	@Transactional(readOnly = true)
	public Artist getArtist(Integer id) throws MusicLibraryServiceException {

		log.debug("getArtist() called with id: {}", id);
        /*
		Artist result = artistRepository.findOne(id);
		if (result == null)
			throw new ArtistNotFoundException("Artist with id: " + id + " does not exist.");
		*/
		Artist result = artistRepository.findById(id).orElseThrow(() -> new ArtistNotFoundException("Artist with id: " + id + " does not exist."));

		return result;
	}

	@Transactional
	public Artist createArtist(Artist artist) throws MusicLibraryServiceException {

		log.debug("createArtist() called with artist: {}", artist);

		if (artist == null) {
			throw new InvalidArtistException("Artist cannot be null");
		}

		artist.setId(null);
		artistValidator.validate(artist);
		return artistRepository.save(artist);
	}

	@Transactional
	public Artist updateArtist(Artist artist) throws MusicLibraryServiceException {

		log.debug("updateArtist() called with artist: {}", artist);

		if (artist == null) {
			throw new InvalidArtistException("Artist cannot be null");
		}

		getArtist(artist.getId());
		artistValidator.validate(artist);
		return artistRepository.save(artist);
	}

	@Transactional
	public void deleteArtist(Artist artist) throws MusicLibraryServiceException {

		log.debug("deleteArtist() called with artist: {}", artist);

		if (artist == null) {
			throw new InvalidArtistException("Artist cannot be null");
		}

		artist = getArtist(artist.getId());

		if (albumRepository.countByArtist(artist) > 0) {
			throw new InvalidArtistException("Artist has albums and cannot be deleted");
		}

		artistRepository.delete(artist);
	}

	@Transactional(readOnly = true)
	public List<Album> getAlbums(Artist artist) throws MusicLibraryServiceException {

		log.debug("getAlbums() called for artist: {}", artist);

		artist = getArtist(artist.getId());

		return albumRepository.findByArtist(artist);
	}

	@Transactional(readOnly = true)
	public List<Album> findAlbums(String artist, String title, Integer year) {

		log.debug("findAlbums() called: artist={}, title={}, year={}", artist, title, year);

		List<Album> result;

		if (artist == null && title == null && year == null) {
			result = albumRepository.findAllByOrderByTitleAscArtistNameAsc();
		} else {
			result = albumRepository.findAll(new AlbumRepository.AlbumSpecification(artist, title, year));
		}

		return result;
	}

	@Transactional(readOnly = true)
	public Album getAlbum(Integer id) throws MusicLibraryServiceException {

		log.debug("getAlbum() called with id: {}", id);

		/*
		Album result = albumRepository.findOne(id);
		if (result == null)
			throw new AlbumNotFoundException("Album with id: " + id + " does not exist.");
			*/
		Album result = albumRepository.findById(id).orElseThrow(()->new AlbumNotFoundException("Album with id: " + id + " does not exist."));

		return result;
	}

	@Transactional(readOnly = true)
	public List<Album> getAlbums() {

		log.debug("getAlbums() called");

		return albumRepository.findAllByOrderByTitleAscArtistNameAsc();
	}

	@Transactional
	public Album createAlbum(Album album) throws MusicLibraryServiceException {

		log.debug("createAlbum() called with album: {}", album);

		if (album == null) {
			throw new InvalidAlbumException("Album cannot be null");
		}

		album.setId(null);
		attachSongs(album);
		albumValidator.validate(album);
		return albumRepository.save(album);
	}

	@Transactional
	public Album updateAlbum(Album album) throws MusicLibraryServiceException {

		log.debug("updateAlbum() called with album: {}", album);

		if (album == null) {
			throw new InvalidAlbumException("Album cannot be null");
		}

		getAlbum(album.getId());
		attachSongs(album);
		albumValidator.validate(album);
		return albumRepository.save(album);
	}

	@Transactional
	public void deleteAlbum(Album album) throws MusicLibraryServiceException {

		log.debug("deleteAlbum() called with album: {}", album);

		albumRepository.delete(getAlbum(album.getId()));
	}

	private void attachSongs(Album album) {
		album.getSongs().stream().forEach(song -> song.setAlbum(album));
	}
}
