package dk.purplegreen.musiclibraryboot.repositories;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import dk.purplegreen.musiclibraryboot.domain.Artist;
import dk.purplegreen.musiclibraryboot.repository.ArtistRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ArtistRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private ArtistRepository repository;

	@Test
	public void testFindById() {
		Artist artist = repository.findOne(1);
		assertEquals("Wrong artist", "The Beatles", artist.getName());
	}

	@Test
	public void testFindByName() {

		entityManager.persist(new Artist("Black Sabbath"));

		Artist artist = repository.findByName("Black Sabbath");
		
		assertNotNull("Artist is null", artist);
		assertEquals("Wrong artist", "Black Sabbath", artist.getName());

	}

	@Test
	public void testSave() {

		Artist artist = repository.save(new Artist("Cornerstone"));

		assertNotNull("Artist is null", artist);
		assertNotNull("Artist id is null", artist.getId());

	}
	
	@Test
	public void testUpdate() {

		entityManager.persist(new Artist("Iron Butterfly"));

		Artist artist = repository.findByName("Iron Butterfly");
		
		artist=new Artist(artist.getId());
		artist.setName("Iron Maiden");
		
		artist = repository.save(new Artist("Iron Maiden"));	
		
		assertNotNull("Artist is null", artist);
		assertNotNull("Artist id is null", artist.getId());
		assertEquals("Wrong artist", "Iron Maiden", artist.getName());
		
		artist = repository.findByName("Iron Butterfly");
		assertNotNull("Artist is not null", artist);		
	}
	
	
	@Test
	public void testDelete() {

		Artist artist=entityManager.find(Artist.class, 3);
		assertNotNull("Artist is null", artist);
		
		repository.delete(artist);
		
		artist=entityManager.find(Artist.class, 3);
		assertNull("Artist is not null", artist);		
	}
	

	@Test
	public void testFindAll() {

		List<Artist> artists = repository.findAllByOrderByNameAsc();

		artists.forEach(artist -> {
			System.out.println(artist);
		}

		);
	}

	@Test
	public void testFindByPartialName() {

		// List<Artist> artists = repository.findByPartialName("%royal%");
		List<Artist> artists = repository.findByNameIgnoreCaseContaining("royal");

		artists.forEach(artist -> {
			System.out.println(artist);
		}

		);
	}
}
