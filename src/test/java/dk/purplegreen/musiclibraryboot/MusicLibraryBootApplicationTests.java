package dk.purplegreen.musiclibraryboot;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import dk.purplegreen.musiclibraryboot.repository.AlbumRepository;
import dk.purplegreen.musiclibraryboot.repository.ArtistRepository;
import dk.purplegreen.musiclibraryboot.service.AlbumValidator;
import dk.purplegreen.musiclibraryboot.service.ArtistValidator;
import dk.purplegreen.musiclibraryboot.service.MusicLibraryService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MusicLibraryBootApplicationTests {

	@Autowired
	private MusicLibraryService service;

	@Autowired
	private ArtistRepository artistRepository;

	@Autowired
	private AlbumRepository albumRepository;

	@Autowired
	private ArtistValidator artistValidator;

	@Autowired
	private AlbumValidator albumValidator;

	@Test
	public void contextLoads() {
		assertNotNull("Service is null", service);
		assertNotNull("ArtistRepository is null", artistRepository);
		assertNotNull("AlbumRepository is null", albumRepository);
		assertNotNull("ArtistValidator is null", artistValidator);
		assertNotNull("AlbumValidator is null", albumValidator);
	}
}
