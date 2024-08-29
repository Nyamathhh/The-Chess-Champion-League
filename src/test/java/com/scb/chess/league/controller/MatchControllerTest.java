package com.scb.chess.league.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scb.chess.league.dto.MatchDTO;
import com.scb.chess.league.model.League;
import com.scb.chess.league.model.Match;
import com.scb.chess.league.model.Participant;
import com.scb.chess.league.repository.MatchRepository;
import com.scb.chess.league.service.MatchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@TestPropertySource(properties = "spring.security.enabled=false")
@SpringBootTest
@AutoConfigureMockMvc
public class MatchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MatchRepository matchRepository;

    @Mock
    private MatchService matchService;

    @InjectMocks
    private MatchController matchController;

    private Participant p1;
    private Participant p2;
    private League l;
    private Match match;

    @BeforeEach
    void setup() {
        p1 = new Participant();
        p1.setId(1L);
        p1.setName("A");
        p1.setEmail("a@test.com");

        p2 = new Participant();
        p2.setId(2L);
        p2.setName("B");
        p2.setEmail("b@test.com");

        l = new League();
        l.setId(1L);
        l.setName("League 1");
        l.setParticipants(List.of(p1, p2));

        match = new Match();
        match.setId(1L);
        match.setParticipant1(p1);
        match.setParticipant2(p2);
        match.setMatchDate(new Date());
        match.setWinner(p1);
        match.setLeague(l);

        when(matchRepository.save(any(Match.class))).thenReturn(match);
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(matchController).build();
    }

    @Test
    void testGetAllMatches() throws Exception {
        when(matchRepository.findAll()).thenReturn(Collections.singletonList(match));

        mockMvc.perform(get("/api/v1/matches"))
                .andExpect(status().isOk());
    }

    private String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
