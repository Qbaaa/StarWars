package com.qbaaa.StarWars.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qbaaa.StarWars.models.StarShips;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StarShipsService {

    private static final Logger logger = LoggerFactory.getLogger(StarShipsService.class);
    private ObjectMapper mapper;

    public StarShipsService() {
        mapper = new ObjectMapper();
    }

    public Optional<StarShips> convertJsonToObject(String jsonString) {

        Optional<StarShips> starShipOptional = Optional.empty();
        try {
            JsonNode starShipNode = mapper.readTree(jsonString);

            StringBuilder urlStarShip = new StringBuilder(starShipNode.findValue("url").textValue());
            urlStarShip.delete(urlStarShip.length() - 1, urlStarShip.length());
            urlStarShip.delete(0, 32);

            StarShips starShip = StarShips.builder().
                    id(Integer.parseInt(urlStarShip.toString())).
                    name(starShipNode.findValue("name").textValue()).
                    model(starShipNode.findValue("model").textValue()).
                    manufacturer(starShipNode.findValue("manufacturer").textValue()).
                    costInCredits(starShipNode.findValue("cost_in_credits").textValue()).
                    length(starShipNode.findValue("length").textValue()).
                    maxAtmospheringSpeed(starShipNode.findValue("max_atmosphering_speed").textValue()).
                    crew(starShipNode.findValue("crew").textValue()).
                    passengers(starShipNode.findValue("passengers").textValue()).
                    cargoCapacity(starShipNode.findValue("cargo_capacity").textValue()).
                    consumables(starShipNode.findValue("consumables").textValue()).
                    hyperdriveRating(starShipNode.findValue("hyperdrive_rating").textValue()).
                    mglt(starShipNode.findValue("MGLT").textValue()).
                    starshipClass(starShipNode.findValue("starship_class").textValue()).
                    build();

            starShipOptional = Optional.of(starShip);
            StringBuilder sb = new StringBuilder().
                    append("Convert StarShip Id ").
                    append(urlStarShip).
                    append(" with JSON to Object.");
            logger.info(sb.toString());

        }
        catch (JsonProcessingException e) {
            logger.warn("Problem with processing JSON to Object (StarShip).");
            e.printStackTrace();
        }
        return starShipOptional;
    }
}
