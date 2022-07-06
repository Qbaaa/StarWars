package com.qbaaa.StarWars.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.qbaaa.StarWars.models.Heroes;
import com.qbaaa.StarWars.models.HousesWorld;
import com.qbaaa.StarWars.models.StarShips;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class HeroesService {
    private static final Logger logger = LoggerFactory.getLogger(HeroesService.class);
    private ObjectMapper mapper;

    public HeroesService() {
        mapper = new ObjectMapper();
    }

    public Optional<Heroes> convertJsonToObject(String jsonString) {

        Optional<Heroes> heroOptional = Optional.empty();
        try {
            JsonNode heroNode = mapper.readTree(jsonString);

            StringBuilder urlPeopleBuilder = new StringBuilder( heroNode.findValue("url").textValue() );
            urlPeopleBuilder.delete( 0, 29 );
            urlPeopleBuilder.delete( urlPeopleBuilder.length() - 1, urlPeopleBuilder.length() );

            StringBuilder urlHomeWorld = new StringBuilder( heroNode.findValue("homeworld").textValue() );
            urlHomeWorld.delete( urlHomeWorld.length() - 1, urlHomeWorld.length() );
            urlHomeWorld.delete(0, 30);
            HousesWorld homeWorld = HousesWorld.builder().
                    id(Integer.parseInt(urlHomeWorld.toString())).
                    build();

            JsonNode starShipsNode = heroNode.path("starships");
            Iterator<JsonNode> iteratorStarShips = starShipsNode.elements();
            Set<StarShips> setStarShips = new LinkedHashSet<>();

            while (iteratorStarShips.hasNext()) {
                JsonNode starShipNode = iteratorStarShips.next();

                StringBuilder urlStarShip = new StringBuilder( starShipNode.textValue() );
                urlStarShip.delete( urlStarShip.length() - 1, urlStarShip.length() );
                urlStarShip.delete( 0, 32 );
                StarShips starShip = StarShips.builder().
                        id( Integer.parseInt(urlStarShip.toString())).
                        build();

                setStarShips.add(starShip);
            }

            Heroes hero = Heroes.builder().
                    id(Integer.parseInt(urlPeopleBuilder.toString())).
                    name(heroNode.findValue("name").textValue()).
                    height(heroNode.findValue("height").textValue()).
                    mass(heroNode.findValue("mass").textValue()).
                    hairColor(heroNode.findValue("hair_color").textValue()).
                    skinColor(heroNode.findValue("skin_color").textValue()).
                    eyeColor(heroNode.findValue("eye_color").textValue()).
                    birthYear(heroNode.findValue("birth_year").textValue()).
                    gender(heroNode.findValue("gender").textValue()).
                    homeWorld(homeWorld).
                    setStarShips(setStarShips).
                    build();

            heroOptional = Optional.of(hero);
            StringBuilder sb = new StringBuilder().
                    append("Converted Hero Id ").
                    append(urlPeopleBuilder).
                    append(" with JSON to Object.");
            logger.info(sb.toString());
        }
        catch(JsonProcessingException e) {
            logger.warn("Problem with processing JSON to Object (Hero).");
            e.printStackTrace();
        }

        return heroOptional;
    }
}
