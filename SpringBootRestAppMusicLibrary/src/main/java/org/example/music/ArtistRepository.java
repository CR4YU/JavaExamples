package org.example.music;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArtistRepository extends JpaRepository<Artist, Long> {

	Artist getByNameAndSurname(String name, String surname);
	List<Artist> findAll();
}
