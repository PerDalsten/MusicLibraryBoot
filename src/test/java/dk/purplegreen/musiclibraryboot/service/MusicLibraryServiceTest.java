package dk.purplegreen.musiclibraryboot.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.AdditionalAnswers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import dk.purplegreen.musiclibraryboot.domain.Album;
import dk.purplegreen.musiclibraryboot.domain.Artist;
import dk.purplegreen.musiclibraryboot.domain.Song;
import dk.purplegreen.musiclibraryboot.repository.AlbumRepository;
import dk.purplegreen.musiclibraryboot.repository.ArtistRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MusicLibraryServiceTest {

	@MockBean
	private ArtistRepository artistRepository;
	
	@MockBean
	private AlbumRepository albumRepository;
	
	@MockBean
	private AlbumValidator albumValidator;
	
	
	@Autowired
	MusicLibraryService service;
	
	@Test
	public void testGetArtists() {
		
		given(artistRepository.findAllByOrderByNameAsc()).willReturn(Arrays.asList(new Artist("Saxon"),new Artist("Iron Maiden")));
		
		List<Artist> artists=service.getArtists();
		assertNotNull("Artists is null", artists);
		assertEquals("Wrong artist", "Iron Maiden", artists.get(1).getName());								
	}

	@Test
	public void testCreateAlbum()throws Exception{
		
		when(albumRepository.save(any(Album.class))).thenAnswer(AdditionalAnswers.<Album>returnsFirstArg());
		
		Album album=new Album(null, "Devils Dozen", 2015);
		album.setId(42);
		
		album.addSong(new Song("So Right So Wrong",1));
		album.addSong(new Song("May You Never (Walk Alone)",2));
		album.addSong(new Song("Heart On A Platter",3));
				
		album=service.createAlbum(album);
	
		assertNull("Album ID not null", album.getId());
		
		for(Song s: album.getSongs()){
			assertEquals("Album not set", album, s.getAlbum());
		}			
	}
	
}
