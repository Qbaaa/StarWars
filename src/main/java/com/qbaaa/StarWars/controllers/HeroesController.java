package com.qbaaa.StarWars.controllers;

import com.qbaaa.StarWars.models.Heroes;
import com.qbaaa.StarWars.repositories.HeroesRepository;
import com.qbaaa.StarWars.services.HeroesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/characters")
public class HeroesController {

    private final HeroesRepository heroesRepository;
    private final HeroesService heroesService;

    @Autowired
    public HeroesController(HeroesRepository heroesRepository, HeroesService heroesService) {
        this.heroesRepository = heroesRepository;
        this.heroesService = heroesService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getHero(@PathVariable int id) {

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);

        Optional<Heroes> searchHero = heroesRepository.findById(id);
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
}
