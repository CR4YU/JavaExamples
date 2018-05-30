package org.example.music;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/artists")
public class ArtistRestController {

	@Autowired
	private ArtistService artistService;

	@RequestMapping(method = RequestMethod.POST)
	public void saveArtist(@RequestBody Artist artist) {
		if(!artistService.isExist(artist.getName(), artist.getSurname()))
			artistService.saveArtist(artist);
	}

	@RequestMapping(method = RequestMethod.GET)
	public List<Artist> getAllArtists() {
		return artistService.getAllArtists();
	}
}
