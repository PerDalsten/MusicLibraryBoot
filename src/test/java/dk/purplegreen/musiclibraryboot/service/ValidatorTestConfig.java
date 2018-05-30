package dk.purplegreen.musiclibraryboot.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Scope;

import dk.purplegreen.musiclibraryboot.domain.Album;
import dk.purplegreen.musiclibraryboot.domain.Artist;
import dk.purplegreen.musiclibraryboot.domain.Song;

@Configuration
@ComponentScan(basePackages = {
		"dk.purplegreen.musiclibraryboot.service" }, useDefaultFilters = false, includeFilters = {
				@Filter(type = FilterType.ASSIGNABLE_TYPE, classes = { AlbumValidator.class, ArtistValidator.class }) })
public class ValidatorTestConfig {

	@Bean
	@Scope("prototype")
	public Album album() {
		Album album = new Album(42);

		album.setArtist(artist());
		album.setTitle("The Singles Collection");
		album.setYear(1997);

		album.addSong(new Song("Long Tall Sally", 1));
		album.addSong(new Song("You Still Want Me", 2));
		album.addSong(new Song("You Really Got Me", 3));

		return album;
	}

	@Bean
	@Scope("prototype")
	public Artist artist() {
		Artist artist = new Artist(12);
		artist.setName("Kinks");
		return artist;
	}

}
