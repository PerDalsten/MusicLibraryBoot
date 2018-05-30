package dk.purplegreen.musiclibraryboot.service;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import dk.purplegreen.musiclibraryboot.domain.Album;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ValidatorTestConfig.class })
public class AlbumValidatorTest {

	@Autowired
	AlbumValidator validator;
	@Autowired
	Album album;

	@Rule
	public ExpectedException ex = ExpectedException.none();

	@Test
	public void testValid() throws InvalidAlbumException {

		validator.validate(album);
	}

	@Test
	public void testNull() throws InvalidAlbumException {

		ex.expect(InvalidAlbumException.class);
		ex.expectMessage("Album is null");

		validator.validate(null);
	}
	
	@Test
	public void testArtistNull() throws InvalidAlbumException {

		ex.expect(InvalidAlbumException.class);
		ex.expectMessage("Artist is null");

		album.setArtist(null);
		
		validator.validate(album);
	}
	
	@Test
	public void testInvalidArtist() throws InvalidAlbumException {

		ex.expect(InvalidAlbumException.class);
		ex.expectMessage("Artist is invalid");

		album.getArtist().setId(null);;
		
		validator.validate(album);
	}

	@Test
	public void testNoSongs() throws InvalidAlbumException {

		ex.expect(InvalidAlbumException.class);
		ex.expectMessage("Album does not have any songs");

		album.getSongs().clear();
		validator.validate(album);
	}
}
