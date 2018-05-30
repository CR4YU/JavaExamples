package org.example.music;

import java.util.List;

public interface ArtistService {

	List<Artist> getAllArtists();
	boolean isExist(String name, String surname);
	void saveArtist(Artist artist);
}
