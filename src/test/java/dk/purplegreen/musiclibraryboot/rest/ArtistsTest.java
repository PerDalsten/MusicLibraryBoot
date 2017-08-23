package dk.purplegreen.musiclibraryboot.rest;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import dk.purplegreen.musiclibraryboot.domain.Artist;
import dk.purplegreen.musiclibraryboot.service.MusicLibraryService;

@RunWith(SpringRunner.class)
@WebMvcTest(Artists.class)
public class ArtistsTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private MusicLibraryService service;

	@Test
	public void testGetArtist() throws Exception {

		Artist artist = new Artist(42);
		artist.setName("Ratt");
		given(service.getArtist(42)).willReturn(artist);

		mvc.perform(get("/rest/artists/42").accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk())
				.andExpect(content().json("{\"id\":42,\"name\":\"Ratt\"}"));
	}
}
