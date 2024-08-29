package com.scb.chess.league.service;

import com.scb.chess.league.model.Match;
import com.scb.chess.league.model.Participant;
import com.scb.chess.league.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class ChampionService {

    @Autowired
    private MatchRepository matchRepository;

    public Optional<Participant> determineChampion() {
        List<Match> matches = matchRepository.findAll();
        return matches.stream()
                .map(Match::getWinner)
                .filter(winner -> winner != null)
                .max(Comparator.comparingInt(winner -> (int) matches.stream().filter(match -> winner.equals(match.getWinner())).count()));
    }
}