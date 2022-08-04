package com.qbaaa.StarWars.controllers;

import com.qbaaa.StarWars.services.HeroesService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;;
import org.springframework.util.StopWatch;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class HeroesControllerTests {
    private static final Logger logger = LoggerFactory.getLogger(HeroesControllerTests.class);

    private MockMvc mockMvc;

    @Autowired
    public HeroesControllerTests(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
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

    @Test
    @DisplayName("return Json paged list of heroes when getPageHeroes method")
    void shouldReturnJsonPagedListOfHeroesWhenGetPageHeroesMethod() throws Exception {
        int page = 1;
        long start = System.currentTimeMillis();
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/characters?page=" + page)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        long end = System.currentTimeMillis();
        System.out.println(end - start + "ms");
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        String content = mvcResult.getResponse().getContentAsString();
        assertTrue(content.contains("{\"count\":82,\"pages\":9,\"elements\":[{\"id\":1"));
    }

    @Test
    @DisplayName("return single and total requests time when execute twenty requests")
    void shouldReturnSingleAndTotalRequestsTimeWhenExecuteTwentyRequests() throws Exception {
        int amountRequest=20;
        StopWatch stopWatch = new StopWatch("Testing performances");

        for(int i = 1; i <= amountRequest; i++) {
            stopWatch.start("Request " + i);
            this.mockMvc.perform(MockMvcRequestBuilders.get("/characters")
                    .contentType(MediaType.APPLICATION_JSON)).andReturn();
            stopWatch.stop();
            logger.info("Request " + i + "  -> Time: " + stopWatch.getLastTaskTimeMillis() + " ms");
        }

        logger.info("Testing performances " + amountRequest + " requests -> time: " + stopWatch.getTotalTimeMillis() + " ms");
    }
}
