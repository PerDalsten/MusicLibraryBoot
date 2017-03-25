package dk.purplegreen.musiclibraryboot.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import dk.purplegreen.musiclibraryboot.domain.Album;
import dk.purplegreen.musiclibraryboot.domain.Artist;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AlbumRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private AlbumRepository repository;

	@Test
	public void testFindById() {
		Album album = repository.findOne(2);
		assertNotNull("Album is null", album);

		assertEquals("Wrong title", "Paradox", album.getTitle());

		assertEquals("Wrong artist", "Royal Hunt", album.getArtist().getName());
	}

	@Test
	public void testAlbumCount() {

		Artist artist = entityManager.find(Artist.class, 3);

		assertEquals("Wrong number of albums", 1, repository.countByArtist(artist));
	}

	@Test
	public void testFindByArtist() {

		Artist artist = entityManager.find(Artist.class, 1);

		List<Album> albums = repository.findByArtist(artist);

		assertEquals("Wrong number of albums", 1, albums.size());
		assertEquals("Wrong album", "Abbey Road", albums.get(0).getTitle());
	}

	@Test
	public void testFindByArtistName() {

		List<Album> albums = repository.findByArtistNameIgnoreCaseContaining("oyal");

		assertEquals("Wrong number of albums", 1, albums.size());
		assertEquals("Wrong album", "Paradox", albums.get(0).getTitle());
	}

	@Test
	public void testFindByArtistNameAndTitle() {

		List<Album> albums = repository.findByArtistNameIgnoreCaseContainingAndTitleIgnoreCaseContaining("eatle",
				"road");

		assertEquals("Wrong number of albums", 1, albums.size());
		assertEquals("Wrong album", "Abbey Road", albums.get(0).getTitle());
	}

}
