package com.qbaaa.StarWars.serwices;

import com.qbaaa.StarWars.models.Heroes;
import com.qbaaa.StarWars.models.HousesWorld;
import com.qbaaa.StarWars.models.StarShips;
import com.qbaaa.StarWars.services.HeroesService;
import com.qbaaa.StarWars.services.StarWarsApiService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
public class HeroesServiceTests {

    private final HeroesService heroesService;
    private final StarWarsApiService starWarsApiService;

    @Autowired
    public HeroesServiceTests(HeroesService heroesService, StarWarsApiService starWarsApiService) {
        this.heroesService = heroesService;
        this.starWarsApiService = starWarsApiService;
    }

    @Test
    @DisplayName("return Object Hero when convert JSON to Object")
    void shouldReturnObjectHeroWhenConvertJsonToObject() throws IOException, InterruptedException {

        String urlGetHero = "https://swapi.dev/api/people/1";
        HttpResponse<String> responseJson = starWarsApiService.httpResponseJsonString(urlGetHero);

        HousesWorld homeWorldId1 = HousesWorld.builder().
                id(1).
                build();

        StarShips starShipId12 = StarShips.builder().
                id(12).
                build();

        StarShips starShipId22 = StarShips.builder().
                id(22).
                build();

        List<StarShips> starShips = new ArrayList<>();
        starShips.add(starShipId12);
        starShips.add(starShipId22);

        Heroes hero = Heroes.builder().
                id(1).
                name("Luke Skywalker").
                height("172").
                mass("77").
                hairColor("blond").
                skinColor("fair").
                eyeColor("blue").
                birthYear("19BBY").
                gender("male").
                homeWorld(homeWorldId1).
                listStarShips(starShips).
                build();

        Optional<Heroes> heroeWithJson = heroesService.convertJsonToObject(responseJson.body());
        if (heroeWithJson.isEmpty()) {
            fail("Return heroes should not be Null");
        }
        else {
            System.out.println(hero);
            System.out.println(heroeWithJson.get());
            assertTrue(hero.equals(heroeWithJson.get()));
        }
    }
}
