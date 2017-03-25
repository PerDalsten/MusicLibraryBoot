package dk.purplegreen.musiclibraryboot.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import dk.purplegreen.musiclibraryboot.domain.Artist;

public interface ArtistRepository extends CrudRepository<Artist, Integer> {
	Artist findByName(String name);

	List<Artist> findAllByOrderByNameAsc();

	List<Artist> findByNameIgnoreCaseContaining(String name);
}
