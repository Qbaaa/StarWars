package com.qbaaa.StarWars;

import com.qbaaa.StarWars.models.Heroes;
import com.qbaaa.StarWars.repositories.HeroesRepository;
import com.qbaaa.StarWars.services.StarWarsApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Component
public class AppStartupRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(AppStartupRunner.class);
    private final StarWarsApiService starWarsApiService;
    private final HeroesRepository heroesRepository;

    @Autowired
    public AppStartupRunner(StarWarsApiService starWarsApiService, HeroesRepository heroesRepository) {
            this.starWarsApiService = starWarsApiService;
            this.heroesRepository = heroesRepository;
        }

        @Override
        public void run(String... args)
        {
            List<Heroes> listHeroes = starWarsApiService.getAllHeroes();

            if (!listHeroes.isEmpty()) {
                heroesRepository.saveAll(listHeroes);
                logger.info("Save all heroes in database.");
            }
            else
                logger.warn("There is nothing to write to the database");
        }
}
