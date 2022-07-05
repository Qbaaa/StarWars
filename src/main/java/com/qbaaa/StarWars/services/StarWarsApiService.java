package com.qbaaa.StarWars.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.qbaaa.StarWars.models.Heroes;
import com.qbaaa.StarWars.models.HousesWorld;
import com.qbaaa.StarWars.models.StarShips;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

@Service
public class StarWarsApiService {

    private static final Logger logger = LoggerFactory.getLogger(StarWarsApiService.class);
    private final HeroesService heroesService;
    private final HousesWorldService housesWorldService;
    private final StarShipsService starShipsService;
    private HttpClient httpClient;
    private ObjectMapper mapper;

    @Autowired
    public StarWarsApiService(HeroesService heroesService,
                              HousesWorldService housesWorldService,
                              StarShipsService starShipsService) {
        this.heroesService = heroesService;
        this.housesWorldService = housesWorldService;
        this.starShipsService = starShipsService;
        httpClient = HttpClient.newHttpClient();
        mapper = new ObjectMapper();
    }

    public HttpResponse<String> httpResponseJsonString(String url) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .build();

        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public List<Heroes> getAllHeroes() {
        List<Heroes> listHeroes = new LinkedList<>();
        String urlGetPeople = "https://swapi.dev/api/people/";

        try {
            while(urlGetPeople != null) {
                HttpResponse<String> response = httpResponseJsonString(urlGetPeople);
                if(response.statusCode() == 200) {
                    JsonNode rootNode = mapper.readTree(response.body());
                    urlGetPeople = rootNode.findValue("next").textValue();

                    JsonNode resultsNode = rootNode.path("results");
                    Iterator<JsonNode> iteratorResults = resultsNode.elements();

                    while (iteratorResults.hasNext()) {
                        Optional<Heroes> heroOptional = heroesService.convertJsonToObject(iteratorResults.next().toString());

                        if (heroOptional.isPresent()) {
                            Optional<HousesWorld> homeWorldOptional = getHomeWorld(heroOptional.get().getHomeWorld().getId());

                            if (homeWorldOptional.isPresent())
                                heroOptional.get().setHomeWorld(homeWorldOptional.get());

                            List<StarShips> listStarShip = new LinkedList<>();
                            for (int i = 0; i < heroOptional.get().getListStarShips().size() ; i++) {
                                Optional<StarShips> starShipOptional = getStarShip(heroOptional.get().getListStarShips().get(i).getId());

                                if (starShipOptional.isPresent())
                                    listStarShip.add(starShipOptional.get());
                            }
                            heroOptional.get().setListStarShips(listStarShip);
                            listHeroes.add(heroOptional.get());
                        }
                    }
                }
            }

        }
        catch (IOException | InterruptedException e) {
            logger.info(e.getMessage());
            e.printStackTrace();
        }
        return listHeroes;
    }

    public Optional<HousesWorld> getHomeWorld(int id) {
        String urlHomeWorld = "https://swapi.dev/api/planets/" + id;
        try {
            HttpResponse<String> response = httpResponseJsonString(urlHomeWorld);
            if (response.statusCode() == 200)
                return housesWorldService.convertJsonToObject(response.body());
            else
                return Optional.empty();
        }
        catch (IOException | InterruptedException e) {
            logger.info(e.getMessage());
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Optional<StarShips> getStarShip(int id) {
        String urlStarShip = "https://swapi.dev/api/starships/" + id;
        try {
            HttpResponse<String> response = httpResponseJsonString(urlStarShip);
            if (response.statusCode() == 200)
                return starShipsService.convertJsonToObject(response.body());
            else
                return Optional.empty();
        }
        catch (IOException | InterruptedException e) {
            logger.info(e.getMessage());
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
