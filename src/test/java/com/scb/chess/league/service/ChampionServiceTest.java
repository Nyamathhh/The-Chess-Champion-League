package com.scb.chess.league.service;

import com.scb.chess.league.model.Match;
import com.scb.chess.league.model.Participant;
import com.scb.chess.league.repository.MatchRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class ChampionServiceTest {

    @Mock
    private MatchRepository matchRepository;

    @InjectMocks
    private ChampionService championService;

    private Participant participant1;
    private Participant participant2;
    private Match match1;
    private Match match2;
    private Match match3;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        participant1 = new Participant();
        participant1.setId(1L);
        participant1.setName("Participant 1");

        participant2 = new Participant();
        participant2.setId(2L);
        participant2.setName("Participant 2");

        match1 = new Match();
        match1.setWinner(participant1);

        match2 = new Match();
        match2.setWinner(participant1);

        match3 = new Match();
        match3.setWinner(participant2);
    }

    @Test
    void testDetermineChampion() {
        when(matchRepository.findAll()).thenReturn(List.of(match1, match2, match3));

        Optional<Participant> champion = championService.determineChampion();

        assertTrue(champion.isPresent());
        assertEquals(participant1, champion.get());
    }

    @Test
    void testDetermineChampion_NoMatches() {
        when(matchRepository.findAll()).thenReturn(List.of());

        Optional<Participant> champion = championService.determineChampion();

        assertTrue(champion.isEmpty());
    }

    @Test
    void testDetermineChampion_NoWinner() {
        match1.setWinner(null);
        match2.setWinner(null);
        match3.setWinner(null);

        when(matchRepository.findAll()).thenReturn(List.of(match1, match2, match3));

        Optional<Participant> champion = championService.determineChampion();

        assertTrue(champion.isEmpty());
    }
}
