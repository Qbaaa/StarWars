package com.qbaaa.StarWars.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.qbaaa.StarWars.models.HousesWorld;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HousesWorldService {

    private static final Logger logger = LoggerFactory.getLogger(HousesWorldService.class);
    private ObjectMapper mapper;

    public HousesWorldService() {
        mapper = new ObjectMapper();
    }

    public Optional<HousesWorld> convertJsonToObject(String jsonString) {

        Optional<HousesWorld> homeWorldOptional = Optional.empty();
        try {
            JsonNode homeWorldNode = mapper.readTree(jsonString);

            StringBuilder urlHomeWorld = new StringBuilder( homeWorldNode.findValue("url").textValue() );
            urlHomeWorld.delete( urlHomeWorld.length() - 1, urlHomeWorld.length() );
            urlHomeWorld.delete(0, 30);

            HousesWorld homeWorld = HousesWorld.builder().
                    id(Integer.parseInt(urlHomeWorld.toString())).
                    name(homeWorldNode.findValue("name").textValue()).
                    rotationPeriod(homeWorldNode.findValue("rotation_period").textValue()).
                    orbitalPeriod(homeWorldNode.findValue("orbital_period").textValue()).
                    diameter(homeWorldNode.findValue("diameter").textValue()).
                    climate(homeWorldNode.findValue("climate").textValue()).
                    gravity(homeWorldNode.findValue("gravity").textValue()).
                    terrain(homeWorldNode.findValue("terrain").textValue()).
                    surfaceWater(homeWorldNode.findValue("surface_water").textValue()).
                    population(homeWorldNode.findValue("population").textValue()).
                    build();

            homeWorldOptional = Optional.of(homeWorld);
            StringBuilder sb = new StringBuilder().
                    append("Convert HomeWorld Id ").
                    append(urlHomeWorld).
                    append(" with JSON to Object.");
            logger.info(sb.toString());
        }
        catch (JsonProcessingException e) {
            logger.warn("Problem with processing JSON to Object (HomeWorld).");
            e.printStackTrace();
        }

        return homeWorldOptional;
    }
}