package com.qbaaa.StarWars.controllers;

import com.qbaaa.StarWars.models.Heroes;
import com.qbaaa.StarWars.repositories.HeroesRepository;
import com.qbaaa.StarWars.services.HeroesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    private final HeroesRepository heroesRepository;
    private final HeroesService heroesService;

    @Autowired
    public HeroesController(HeroesRepository heroesRepository, HeroesService heroesService) {
        this.heroesRepository = heroesRepository;
        this.heroesService = heroesService;
    }

    @GetMapping
    public ResponseEntity<String> getPageHeroes(@RequestParam(defaultValue = "1") Integer page) {

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);

        if (page <= 0) {
            String json = "{ \"page\" : \""+ page + " is incorrect\" }";
            return new ResponseEntity<>(json, responseHeaders, HttpStatus.NOT_FOUND);
        }
        Page<Heroes> pageHero = heroesRepository.findAll(PageRequest.of(page - 1, 10, Sort.by("id")));

        if(pageHero.hasContent()) {
            StringBuilder builderJson = new StringBuilder("{\"count\":" + pageHero.getTotalElements() +
                    ",\"pages\":" + pageHero.getTotalPages() +
                    ",\"elements\":[");

            List<Heroes> pageHeroes = pageHero.getContent();
            for (Heroes ele: pageHeroes) {
                builderJson.append(heroesService.convertObjectToJson(ele));
            }
            builderJson.append("]}");

            return new ResponseEntity<>( builderJson.toString(), responseHeaders, HttpStatus.OK);
        }
        else {
            String json = "{ \"page\" : \""+ page + "\", \"search\" : \"NOT_FOUND\" }";
            return new ResponseEntity<>(json, responseHeaders, HttpStatus.NOT_FOUND);
        }
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
