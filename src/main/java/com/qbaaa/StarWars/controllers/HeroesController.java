package com.qbaaa.StarWars.controllers;

import com.qbaaa.StarWars.models.Heroes;
import com.qbaaa.StarWars.services.HeroesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/characters")
public class HeroesController {
    private static final Logger logger = LoggerFactory.getLogger(HeroesController.class);

    private final HeroesService heroesService;

    private HttpHeaders responseHeaders;

    @Autowired
    public HeroesController(HeroesService heroesService) {
        this.heroesService = heroesService;
        this.responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
    }

    @Operation(summary = "Find paged list of heroes", description = "Returns paged list of heroes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = "application/json", examples = { @ExampleObject(value =
                            "{\"count\": 82, \"pages\": 1 ,\"elements\":[ ]}")} )),
            @ApiResponse(responseCode = "404", description = "Paged list of heroes not found",
                    content = { @Content(mediaType = "application/json", examples = { @ExampleObject(value =
                            "{ \"page\" : 1, \"search\" : \"NOT_FOUND\" }") }) }) })
    @GetMapping
    public ResponseEntity<String> getPageHeroes(
            @Parameter(description="Paged list of heroes to be obtained.")
            @RequestParam(defaultValue = "1")
            Integer page) {
        logger.info(new StringBuilder("Get ").append(page).append(" paged list of heroes.").toString());

        if (page <= 0) {
            String json = "{ \"page\" : \"" + page + " is incorrect\" }";
            return new ResponseEntity<>(json, responseHeaders, HttpStatus.NOT_FOUND);
        }

        Page<Heroes> pageHero = heroesService.getPageHeroes(page);
        if (pageHero.hasContent()) {
            StringBuilder builderJson = new StringBuilder("{\"count\":" + pageHero.getTotalElements() +
                    ",\"pages\":" + pageHero.getTotalPages() +
                    ",\"elements\":[");

            List<Heroes> pageHeroes = pageHero.getContent();
            for (Heroes ele : pageHeroes) {
                builderJson.append(heroesService.convertObjectToJson(ele));
            }
            builderJson.append("]}");

            return new ResponseEntity<>(builderJson.toString(), responseHeaders, HttpStatus.OK);
        } else {
            String json = "{ \"page\" : \"" + page + "\", \"search\" : \"NOT_FOUND\" }";
            return new ResponseEntity<>(json, responseHeaders, HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Find hero by ID", description = "Returns a single hero")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Heroes.class))),
            @ApiResponse(responseCode = "404", description = "Hero not found",
                    content = { @Content(mediaType = "application/json", examples = { @ExampleObject(value =
                            "{ \"id\" : 1, \"search\" : \"NOT_FOUND\" }") }) }) })
    @GetMapping("/{id}")
    public ResponseEntity<String> getHero(
            @Parameter(description = "Id of the hero to be obtained. Cannot be empty.", required = true)
            @PathVariable int id) {
        logger.info(new StringBuilder("Get ").append(id).append(" id of the hero.").toString());

        Optional<Heroes> searchHero = heroesService.getHero(id);
        if (searchHero.isPresent()) {
            return new ResponseEntity<>(heroesService.convertObjectToJson(searchHero.get()),
                    responseHeaders,
                    HttpStatus.OK);
        }
        else {
            String json = "{ \"id\" : \""+ id + "\", \"search\" : \"NOT_FOUND\" }";

            return new ResponseEntity<>(json,
                    responseHeaders,
                    HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Update of name hero by ID", description = "Returns a update hero")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Heroes.class))),
            @ApiResponse(responseCode = "404", description = "Hero not found",
                    content = { @Content(mediaType = "application/json", examples = { @ExampleObject(value =
                            "{ \"id\" : 1, \"search\" : \"NOT_FOUND\" }") }) }) })
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateHero(
            @Parameter(description = "Id of the hero to be update. Cannot be empty.", required = true)
            @PathVariable int id,
            @Parameter(description = "Name of the hero to be update. Cannot be empty.", required = true)
            @RequestBody String name) {
        logger.info(new StringBuilder("Update name =").
                append(name).
                append(" in ").
                append(id).
                append(" id of hero.").
                toString());

        Optional<Heroes> updateHero = heroesService.updateHero(id, name);

        if (updateHero.isPresent()) {
            return new ResponseEntity<>(heroesService.convertObjectToJson(updateHero.get()),
                    responseHeaders,
                    HttpStatus.OK);
        }

        String json = "{ \"id\" : \""+ id + "\", \"search\" : \"NOT_FOUND\" }";
        return new ResponseEntity<>(json,
                responseHeaders,
                HttpStatus.NOT_FOUND);
    }
}
