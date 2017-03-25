package dk.purplegreen.musiclibraryboot.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import dk.purplegreen.musiclibraryboot.domain.Album;
import dk.purplegreen.musiclibraryboot.domain.Artist;

public interface AlbumRepository extends CrudRepository<Album, Integer> {
	
	long countByArtist(Artist artist);
	
	List<Album> findAllByOrderByTitleAsc();
	
	List<Album> findByArtist(Artist artist);
		
	List<Album> findByYear(Integer year);
	
	List<Album> findByTitleIgnoreCaseContaining(String title);
	
	List<Album> findByTitleIgnoreCaseContainingAndYear(String title, Integer year);
		
	List<Album> findByArtistNameIgnoreCaseContaining(String artistName);
	
	List<Album> findByArtistNameIgnoreCaseContainingAndYear(String artistName, Integer year);
	
	List<Album> findByArtistNameIgnoreCaseContainingAndTitleIgnoreCaseContaining(String artistName, String title);
	
	List<Album> findByArtistNameIgnoreCaseContainingAndTitleIgnoreCaseContainingAndYear(String artistName, String title, Integer year);

}
