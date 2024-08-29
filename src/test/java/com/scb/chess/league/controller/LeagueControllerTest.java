package com.scb.chess.league.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scb.chess.league.model.League;
import com.scb.chess.league.repository.LeagueRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestPropertySource(properties = "spring.security.enabled=false")
@SpringBootTest
@AutoConfigureMockMvc
public class LeagueControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LeagueRepository leagueRepository;

    @Test
    void testGetAllLeagues() throws Exception {
        League league = new League();
        when(leagueRepository.findAll()).thenReturn(Collections.singletonList(league));

        mockMvc.perform(get("/api/v1/leagues"))
                .andExpect(status().isOk());
    }

    @Test
    void testCreateLeague() throws Exception {
        League league = new League(); 
        when(leagueRepository.save(any(League.class))).thenReturn(league);

        mockMvc.perform(post("/api/v1/leagues")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(league))
                        .with(csrf().asHeader()))
                .andExpect(status().isCreated());
    }

    @Test
    void testGetLeagueById() throws Exception {
        League league = new League(); 
        when(leagueRepository.findById(anyLong())).thenReturn(Optional.of(league));

        mockMvc.perform(get("/api/v1/leagues/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetLeagueById_NotFound() throws Exception {
        when(leagueRepository.findById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/leagues/1"))
                .andExpect(status().isNotFound());
    }

    private String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
