package com.qbaaa.StarWars.serwices;

import com.qbaaa.StarWars.models.StarShips;
import com.qbaaa.StarWars.services.StarShipsService;
import com.qbaaa.StarWars.services.StarWarsApiService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
class StarShipsServiceTests {

    private final StarShipsService starShipsService;
    private final StarWarsApiService starWarsApiService;

    @Autowired
    public StarShipsServiceTests(StarShipsService starShipsService, StarWarsApiService starWarsApiService) {
        this.starShipsService = starShipsService;
        this.starWarsApiService = starWarsApiService;
    }

    @Test
    @DisplayName("return Object StarShip when convert JSON to Object")
    void shouldReturnObjectStarShipWhenConvertJsonToObject() throws IOException, InterruptedException {

        String urlGetStarShip = "https://swapi.dev/api/starships/12";
        HttpResponse<String> responseJson = starWarsApiService.httpResponseJsonString(urlGetStarShip);

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
                build();

        Optional<StarShips> starShipWithJson = starShipsService.convertJsonToObject(responseJson.body());
        if (starShipWithJson.isEmpty()) {
            fail("Return starShip should not be Null");
        }
        else {
            assertTrue(starShipId12.equals(starShipWithJson.get()));
        }
    }
}
