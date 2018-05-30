package org.example.music;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtistServiceImpl implements ArtistService {

	@Autowired
	private ArtistRepository repo;

	@Override
	public List<Artist> getAllArtists() {
		return repo.findAll();
	}

	@Override
	public boolean isExist(String name, String surname) {
		return repo.getByNameAndSurname(name, surname) != null;
	}

	@Override
	public void saveArtist(Artist artist) {
		repo.save(artist);
	}
}
