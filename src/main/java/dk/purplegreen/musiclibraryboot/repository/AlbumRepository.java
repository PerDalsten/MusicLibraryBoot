package dk.purplegreen.musiclibraryboot.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import dk.purplegreen.musiclibraryboot.domain.Album;
import dk.purplegreen.musiclibraryboot.domain.Artist;

public interface AlbumRepository extends CrudRepository<Album, Integer>, JpaSpecificationExecutor<Album> {

	long countByArtist(Artist artist);

	List<Album> findAllByOrderByTitleAsc();

	List<Album> findByArtist(Artist artist);

	public static class AlbumSpecification implements Specification<Album> {

		private String artist;
		private String title;
		private Integer year;

		public AlbumSpecification(String artist, String title, Integer year) {
			this.artist = artist;
			this.title = title;
			this.year = year;
		}

		@Override
		public Predicate toPredicate(Root<Album> album, CriteriaQuery<?> cq, CriteriaBuilder cb) {

			List<Predicate> predicates = new ArrayList<>();

			if (artist != null && !artist.isEmpty()) {
				predicates.add(cb.like(cb.lower(album.get("artist").get("name")), "%" + artist.toLowerCase() + "%"));
			}
			if (title != null && !title.isEmpty()) {
				predicates.add(cb.like(cb.lower(album.get("title")), "%" + title.toLowerCase() + "%"));
			}
			if (year != null) {
				predicates.add(cb.equal(album.get("year"), year));
			}

			cq.orderBy(cb.asc(album.get("title")));

			return cb.and(predicates.toArray(new Predicate[predicates.size()]));
		}
	}
}
