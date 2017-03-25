package dk.purplegreen.musiclibraryboot.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import dk.purplegreen.musiclibraryboot.domain.Artist;


public interface ArtistRepository extends CrudRepository<Artist, Integer> {
	Artist findByName(String name);
	
	List<Artist> findAllByOrderByNameAsc();
	/*
	@Query("select a from Artist a where lower(a.name) like :name")
	List<Artist> findByPartialName(@Param("name") String name);
*/
	
	List<Artist> findByNameIgnoreCaseContaining(String name);
}
