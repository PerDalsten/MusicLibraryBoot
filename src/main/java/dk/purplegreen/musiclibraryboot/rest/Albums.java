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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import dk.purplegreen.musiclibraryboot.domain.Album;
import dk.purplegreen.musiclibraryboot.service.MusicLibraryService;
import dk.purplegreen.musiclibraryboot.service.MusicLibraryServiceException;

@RestController
@RequestMapping("/rest/albums")
@CrossOrigin
public class Albums {

	private static final Logger log = LoggerFactory.getLogger(Albums.class);

	@Autowired
	private MusicLibraryService service;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Album> getAlbum(@PathVariable("id") Integer id) throws MusicLibraryServiceException {
		log.debug("getAlbum called with id: {}", id);
		return new ResponseEntity<>(service.getAlbum(id), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Album>> getAlbums(@RequestParam(value = "artist", required = false) String artist,
			@RequestParam(value = "title", required = false) String title,
			@RequestParam(value = "year", required = false) Integer year) {

		log.debug("getAlbums called: artist={}, title={}, year={}", artist, title, year);

		if (artist == null && title == null && year == null) {
			return new ResponseEntity<>(service.getAlbums(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(service.findAlbums(artist, title, year), HttpStatus.OK);
		}
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Album> createAlbum(@RequestBody Album album, UriComponentsBuilder uriBuilder)
			throws MusicLibraryServiceException {

		log.debug("createAlbum called with input: {}", album);

		album = service.createAlbum(album);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setLocation(uriBuilder.path("/artists/{id}").buildAndExpand(album.getId()).toUri());

		return new ResponseEntity<>(album, httpHeaders, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Album> updateAlbum(@PathVariable("id") Integer id, @RequestBody Album album)
			throws MusicLibraryServiceException {

		log.debug("updateAlbum called for id: {} with input: {}", id, album);

		album.setId(id);
		album = service.updateAlbum(album);

		return new ResponseEntity<>(album, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteAlbum(@PathVariable("id") Integer id) throws MusicLibraryServiceException {

		log.debug("deleteAlbum called for id: {}", id);

		service.deleteAlbum(new Album(id));
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
