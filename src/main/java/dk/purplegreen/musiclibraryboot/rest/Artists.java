package dk.purplegreen.musiclibraryboot.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import dk.purplegreen.musiclibraryboot.domain.Album;
import dk.purplegreen.musiclibraryboot.domain.Artist;
import dk.purplegreen.musiclibraryboot.service.MusicLibraryService;
import dk.purplegreen.musiclibraryboot.service.MusicLibraryServiceException;

@RestController
@RequestMapping("/rest/artists")
@CrossOrigin
public class Artists {

	private static final Logger log = LoggerFactory.getLogger(Artists.class);

	@Autowired
	private MusicLibraryService service;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Artist> getArtist(@PathVariable("id") Integer id) throws MusicLibraryServiceException {

		log.debug("getArtist called with id: {}", id);

		return new ResponseEntity<>(service.getArtist(id), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Artist>> getArtists() {

		log.debug("getArtists called");

		return new ResponseEntity<>(service.getArtists(), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Artist> createArtist(@RequestBody Artist artist, UriComponentsBuilder uriBuilder)
			throws MusicLibraryServiceException {

		log.debug("createArtist called with input: {}", artist);

		artist = service.createArtist(artist);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setLocation(uriBuilder.path("/artists/{id}").buildAndExpand(artist.getId()).toUri());

		return new ResponseEntity<>(artist, httpHeaders, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Artist> updateArtist(@PathVariable("id") Integer id, @RequestBody Artist artist)
			throws MusicLibraryServiceException {

		log.debug("updateArtist called for id: {} with input: {}", id, artist);	

		artist.setId(id);
		artist = service.updateArtist(artist);

		return new ResponseEntity<>(artist, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteArtist(@PathVariable("id") Integer id) throws MusicLibraryServiceException {

		log.debug("deleteArtist called for id: {}", id);

		service.deleteArtist(new Artist(id));
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}/albums", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Album>> getArtistAlbums(@PathVariable("id") Integer id)
			throws MusicLibraryServiceException {

		log.debug("getArtistAlbums called with id: {}", id);

		return new ResponseEntity<>(service.getAlbums(new Artist(id)), HttpStatus.OK);
	}
}
