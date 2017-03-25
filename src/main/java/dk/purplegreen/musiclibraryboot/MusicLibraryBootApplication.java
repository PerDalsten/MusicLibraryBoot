package dk.purplegreen.musiclibraryboot;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import dk.purplegreen.musiclibraryboot.domain.Artist;
import dk.purplegreen.musiclibraryboot.repositories.ArtistRepository;

@SpringBootApplication
public class MusicLibraryBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(MusicLibraryBootApplication.class, args);
	}

	/*
	@Bean
	public CommandLineRunner demo(ArtistRepository repository) {
		return (args) -> {

			repository.save(new Artist("Royal Hunt"));
			repository.save(new Artist("Beatles"));

			System.out.println("DONE SAVING");

			Iterable<Artist> artists = repository.findAll();

			artists.forEach(artist -> System.out.println(artist));

		};
	}
	*/

}
