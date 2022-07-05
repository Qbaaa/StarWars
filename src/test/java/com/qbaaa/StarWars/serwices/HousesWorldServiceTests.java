package com.qbaaa.StarWars.serwices;

import com.qbaaa.StarWars.models.HousesWorld;
import com.qbaaa.StarWars.models.StarShips;
import com.qbaaa.StarWars.services.HousesWorldService;
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
class HousesWorldServiceTests {

    private final HousesWorldService housesWorldService;
    private final StarWarsApiService starWarsApiService;

    @Autowired
    public HousesWorldServiceTests(HousesWorldService housesWorldService, StarWarsApiService starWarsApiService) {
        this.housesWorldService = housesWorldService;
        this.starWarsApiService = starWarsApiService;
    }

    @Test
    @DisplayName("return Object HomeWorld when convert JSON to Object")
    void shouldReturnObjectHomeWorldWhenConvertJsonToObject() throws IOException, InterruptedException {

        String urlGetPlanet = "https://swapi.dev/api/planets/1";
        HttpResponse<String> responseJson = starWarsApiService.httpResponseJsonString(urlGetPlanet);

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

        Optional<HousesWorld> homeWorldWithJson = housesWorldService.convertJsonToObject(responseJson.body());
        if (homeWorldWithJson.isEmpty()) {
            fail("Return homeWorld should not be Null");
        }
        else {
            assertTrue(homeWorldId1.equals(homeWorldWithJson.get()));
        }
    }
}
