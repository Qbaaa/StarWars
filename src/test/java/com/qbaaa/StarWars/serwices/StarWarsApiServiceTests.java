package com.qbaaa.StarWars.serwices;

import com.qbaaa.StarWars.models.Heroes;
import com.qbaaa.StarWars.models.HousesWorld;
import com.qbaaa.StarWars.models.StarShips;
import com.qbaaa.StarWars.services.StarWarsApiService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StarWarsApiServiceTests {

    private final StarWarsApiService starWarsApiService;

    @Autowired
    public StarWarsApiServiceTests(StarWarsApiService starWarsApiService) {
        this.starWarsApiService = starWarsApiService;
    }

    @Test
    @DisplayName("return Status 200 when request: GET https://swapi.dev/api/people/")
    void shouldReturnStatus200WhenRequestGetPeople() throws IOException, InterruptedException {
        String urlGetPeople = "https://swapi.dev/api/people/";

        HttpResponse<String> response = starWarsApiService.httpResponseJsonString(urlGetPeople);
        assertEquals(200, response.statusCode());
    }

    @Test
    @DisplayName("return Status 200 when request: GET https://swapi.dev/api/planets/")
    void shouldReturnStatus200WhenRequestGetPlanet() throws IOException, InterruptedException {
        String urlGetPlanet = "https://swapi.dev/api/planets/";

        HttpResponse<String> response = starWarsApiService.httpResponseJsonString(urlGetPlanet);
        assertEquals(200, response.statusCode());
    }

    @Test
    @DisplayName("return Status 200 when request: GET https://swapi.dev/api/starships/")
    void shouldReturnStatus200WhenRequestGetStarShip() throws IOException, InterruptedException {
        String urlGetStarShip = "https://swapi.dev/api/starships/";

        HttpResponse<String> response = starWarsApiService.httpResponseJsonString(urlGetStarShip);
        assertEquals(200, response.statusCode());
    }

    @Test
    @DisplayName("return Object StarShip when getStarShip method")
    void shouldReturnObjectStarShipWhenGetStarShipMethod() {

        StarShips starShipId12 = StarShips.builder().
                id(12).
                name("X-wing").
                model("T-65 X-wing").
                manufacturer("Incom Corporation").
                costInCredits("149999").
                length("12.5").
                maxAtmospheringSpeed("1050").
                crew("1").
                passengers("0").
                cargoCapacity("110").
                consumables("1 week").
                hyperdriveRating("1.0").
                mglt("100").
                starshipClass("Starfighter").
                setHeroes(new LinkedHashSet<>()).
                build();

        Optional<StarShips> object = starWarsApiService.getStarShip(12);

        if (object.isEmpty()) {
            fail("Return object should not be Null");
        }
        else {
            assertTrue(starShipId12.equals(object.get()));
        }
    }

    @Test
    @DisplayName("return Object HomeWorld when getHomeWorld method")
    void shouldReturnObjectHomeWorldWhenGetHomeWorldMethod() {

        HousesWorld homeWorldId1 = HousesWorld.builder().
                id(1).
                name("Tatooine").
                rotationPeriod("23").
                orbitalPeriod("304").
                diameter("10465").
                climate("arid").
                gravity("1 standard").
                terrain("desert").
                surfaceWater("1").
                population("200000").
                build();

        Optional<HousesWorld> object = starWarsApiService.getHomeWorld(1);

        if (object.isEmpty()) {
            fail("Return object should not be Null");
        }
        else {
            assertTrue(homeWorldId1.equals(object.get()));
        }
    }

    @Test
    @DisplayName("return Objects Heroes when getAllHeroes method")
    void shouldReturnObjectsHeroesWhenGetAllHeroesMethod() {

        List<Heroes> objects = starWarsApiService.getAllHeroes();
        assertEquals(82, objects.size());
    }

    @Test
    @DisplayName("return Object Hero about id 1 when getAllHeroes method")
    void shouldReturnObjectHeroIdOneWhenGetAllHeroesMethod() {

        List<Heroes> heroes = starWarsApiService.getAllHeroes();
        Optional<HousesWorld> homeWorldId1 = starWarsApiService.getHomeWorld(1);
        Optional<StarShips> starShipId12 = starWarsApiService.getStarShip(12);
        Optional<StarShips> starShipId22 = starWarsApiService.getStarShip(22);
        Set<StarShips> starShips = new LinkedHashSet<>();
        starShips.add(starShipId12.get());
        starShips.add(starShipId22.get());

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
                homeWorld(homeWorldId1.get()).
                setStarShips(starShips).
                build();

        assertTrue(hero.equals(heroes.get(0)));
    }
}