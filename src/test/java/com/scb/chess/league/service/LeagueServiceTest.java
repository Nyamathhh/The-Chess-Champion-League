package com.scb.chess.league.service;

import com.scb.chess.league.model.League;
import com.scb.chess.league.model.Participant;
import com.scb.chess.league.repository.LeagueRepository;
import com.scb.chess.league.repository.MatchRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class LeagueServiceTest {

    @Mock
    private LeagueRepository leagueRepository;

    @Mock
    private MatchRepository matchRepository;

    @InjectMocks
    private LeagueService leagueService;

    private League league;
    private Participant participant1;
    private Participant participant2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        participant1 = new Participant();
        participant1.setId(1L);
        participant1.setName("Participant 1");

        participant2 = new Participant();
        participant2.setId(2L);
        participant2.setName("Participant 2");

        league = new League();
        league.setId(1L);
        league.setParticipants(Arrays.asList(participant1, participant2));
    }

    @Test
    void testDetermineLeagueChampion() {
        when(leagueRepository.findById(1L)).thenReturn(Optional.of(league));
        when(matchRepository.countByWinnerAndLeagueId(participant1, 1L)).thenReturn(3L);
        when(matchRepository.countByWinnerAndLeagueId(participant2, 1L)).thenReturn(5L);

        Optional<Participant> champion = Optional.ofNullable(leagueService.determineLeagueChampion(1L));

        assertTrue(champion.isPresent());
        assertEquals(participant2, champion.get());
    }

    @Test
    void testDetermineLeagueChampion_NoMatches() {
        when(leagueRepository.findById(1L)).thenReturn(Optional.of(league));
        when(matchRepository.countByWinnerAndLeagueId(participant1, 1L)).thenReturn(0L);
        when(matchRepository.countByWinnerAndLeagueId(participant2, 1L)).thenReturn(0L);

        Optional<Participant> champion = Optional.ofNullable(leagueService.determineLeagueChampion(1L));

        assertTrue(champion.isEmpty());
    }
}
