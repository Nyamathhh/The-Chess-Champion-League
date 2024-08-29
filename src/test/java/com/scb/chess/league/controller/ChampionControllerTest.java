package com.scb.chess.league.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scb.chess.league.model.League;
import com.scb.chess.league.model.Match;
import com.scb.chess.league.model.Participant;
import com.scb.chess.league.service.ChampionService;
import com.scb.chess.league.service.EmailService;
import com.scb.chess.league.service.LeagueService;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@TestPropertySource(properties = "spring.security.enabled=false")
@SpringBootTest
@AutoConfigureMockMvc
public class ChampionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChampionService championService;

    @MockBean
    private EmailService emailService;

    @MockBean
    private LeagueService leagueService;

    private Participant p1;
    private Participant p2;
    private League l;
    private Match match;

    @BeforeEach
    void setUp() throws MessagingException {
        p1 = new Participant();
        p1.setId(1L);
        p1.setName("Champion");
        p1.setEmail("champion@test.com");

        p2 = new Participant();
        p2.setId(2L);
        p2.setName("Other Participant");
        p2.setEmail("other@test.com");

        // Initialize Leagues
        l = new League();
        l.setId(1L);
        l.setName("League 1");
        l.setParticipants(List.of(p1, p2));

        // Initialize Match
        match = new Match();
        match.setId(1L);
        match.setParticipant1(p1);
        match.setParticipant2(p2);
        match.setMatchDate(new Date());
        match.setWinner(p1);
        match.setLeague(l);

        when(leagueService.determineLeagueChampion(anyLong())).thenReturn(p1);

        when(championService.determineChampion()).thenReturn(Optional.of(p1));

        doNothing().when(emailService).sendCongratulationEmail(any(Participant.class));
    }


    @Test
    void testGetCurrentChampion_Success() throws Exception {
        Participant champion = new Participant();
        champion.setName("Champion Name");
        when(championService.determineChampion()).thenReturn(Optional.of(champion));

        mockMvc.perform(get("/api/v1/champions/current"))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(champion)));
    }

    @Test
    void testGetCurrentChampion_NotFound() throws Exception {
        when(championService.determineChampion()).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/champions/current"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testNotifyChampion_Success() throws Exception {
        Participant champion = new Participant();
        champion.setName("Champion Name");
        when(championService.determineChampion()).thenReturn(Optional.of(champion));
        doNothing().when(emailService).sendCongratulationEmail(champion);

        mockMvc.perform(post("/api/v1/champions/notify")
                        .with(csrf().asHeader()))
                .andExpect(status().isOk())
                .andExpect(content().string("Congratulation email sent to the champion."));
    }

    @Test
    void testNotifyChampion_EmailFailure() throws Exception {
        Participant champion = new Participant();
        champion.setName("Champion Name");
        when(championService.determineChampion()).thenReturn(Optional.of(champion));
        doThrow(new MessagingException("Email error")).when(emailService).sendCongratulationEmail(champion);

        mockMvc.perform(post("/api/v1/champions/notify")
                        .with(csrf().asHeader()))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Failed to send email: Email error"));
    }

    @Test
    void testNotifyChampion_NotFound() throws Exception {
        when(championService.determineChampion()).thenReturn(Optional.empty());

        mockMvc.perform(post("/api/v1/champions/notify")
                        .with(csrf().asHeader()))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Champion not found."));
    }

    @Test
    void testGetLeagueChampion_NotFound() throws Exception {
        when(leagueService.determineLeagueChampion(anyLong())).thenReturn(null);

        mockMvc.perform(get("/api/v1/leagues/1/champion"))
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
