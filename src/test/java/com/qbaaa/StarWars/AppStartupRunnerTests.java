package com.qbaaa.StarWars;

import com.qbaaa.StarWars.models.Heroes;
import com.qbaaa.StarWars.repositories.HeroesRepository;
import com.qbaaa.StarWars.services.StarWarsApiService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AppStartupRunnerTests {

	private final HeroesRepository heroesRepository;
	private final StarWarsApiService starWarsApiService;

	@Autowired
	public AppStartupRunnerTests(HeroesRepository heroesRepository, StarWarsApiService starWarsApiService) {
		this.heroesRepository = heroesRepository;
		this.starWarsApiService = starWarsApiService;
	}

	@Test
	@DisplayName("Return object Hero with database when save all heroes in database")
	@Transactional
	void shouldReturnObjectHeroWithDatabaseWhenSaveAllHeroesInDatabase() {
		List<Heroes> listHeroes = starWarsApiService.getAllHeroes();

		if (!listHeroes.isEmpty()) {
			heroesRepository.saveAll(listHeroes);

			Optional<Heroes> heroesDB = heroesRepository.findById(1);
			assertNotNull(heroesDB.get());
			assertTrue(listHeroes.get(0).equals(heroesDB.get()));
		}
		else {
			fail("There is nothing to write to the database");
		}
	}
}
