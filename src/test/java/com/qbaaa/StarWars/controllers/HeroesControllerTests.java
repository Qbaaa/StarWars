package com.qbaaa.StarWars.controllers;

import com.qbaaa.StarWars.repositories.HeroesRepository;
import com.qbaaa.StarWars.services.HeroesService;
import com.qbaaa.StarWars.services.StarWarsApiService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class HeroesControllerTests {

    private MockMvc mockMvc;
    private HeroesRepository heroesRepository;
    private HeroesService heroesService;
    private StarWarsApiService starWarsApiService;

    @Autowired
    public HeroesControllerTests(MockMvc mockMvc, HeroesRepository heroesRepository, HeroesService heroesService, StarWarsApiService starWarsApiService) {
        this.mockMvc = mockMvc;
        this.heroesRepository = heroesRepository;
        this.heroesService = heroesService;
        this.starWarsApiService = starWarsApiService;
    }

    @Test
    @DisplayName("return Json Hero about id 1 when getHero with parametr 1 method")
    void shouldReturnJsonHeroeAboutId1WhenGetHeroWithParametr1Method() throws Exception {
        int id = 1;

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/characters/" +id)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        String content = mvcResult.getResponse().getContentAsString();
        assertTrue(content.contains("{\"id\":1,\"name\":\"Luke Skywalker\""));
        assertTrue(content.contains("\"homeworld\":{\"name\":\"Tatooine\""));
        assertTrue(content.contains("\"starships\":[{\"name\":\"X-wing\""));
        assertTrue(content.contains("{\"name\":\"Imperial shuttle\",\"model\":\"Lambda-class T-4a shuttle\""));
    }
}
