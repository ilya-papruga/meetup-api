package com.modsen.meetup.controller;

import com.modsen.meetup.configuration.IntegrationTestConfiguration;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@TestPropertySource("/application-test.properties")
@AutoConfigureMockMvc
@ContextConfiguration(classes = {IntegrationTestConfiguration.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MeetupControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    @Order(1)
    public void getAllTestMeetups() throws Exception {
        this.mockMvc.perform(get("/meetup"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].topic").value("test1"))
                .andExpect(jsonPath("$.[1].organizer").value("test2"));
    }

    @Test
    @Order(2)
    public void getOneTestMeetup() throws Exception {
        this.mockMvc.perform(get("/meetup/{id}", 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.topic").value("test1"));
    }

    @Test
    @Order(3)
    public void createTestMeetup() throws Exception {
        mockMvc.perform(post("/meetup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("{\"topic\":\"test3\",\"description\":\"test3\",\"organizer\":\"test3\",\"date_time\":\"2022-11-08T22:49:59\",\"place\":\"test3\"}"))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    @Order(4)
    public void updateTestMeetup() throws Exception {
        mockMvc.perform(put("/meetup/{id}/version/{version}", 3, 0)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("{\"topic\":\"fix_test3\",\"description\":\"fix_test3\",\"organizer\":\"fix_test3\",\"date_time\":\"2022-11-08T22:49:59\",\"place\":\"fix_test3\"}"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Order(5)
    public void CheckUpdatedTestMeetup() throws Exception {
        this.mockMvc.perform(get("/meetup/{id}", 3))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.topic").value("fix_test3"))
                .andExpect(jsonPath("$.version").value("1"));
    }

    @Test
    @Order(6)
    public void DeleteUpdatedTestMeetup() throws Exception {
        mockMvc.perform(delete("/meetup/{id}/version/{version}", 3, 1))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

}
