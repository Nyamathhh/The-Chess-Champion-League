package com.scb.chess.league.service;

import com.scb.chess.league.model.League;
import com.scb.chess.league.model.Participant;
import com.scb.chess.league.repository.LeagueRepository;
import com.scb.chess.league.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LeagueService {

    @Autowired
    private LeagueRepository leagueRepository;

    @Autowired
    private MatchRepository matchRepository;

    public List<League> getAllLeagues() {
        return leagueRepository.findAll();
    }

    public Optional<League> getLeagueById(Long id) {
        return leagueRepository.findById(id);
    }

    public League saveLeague(League league) {
        return leagueRepository.save(league);
    }

    public Participant determineLeagueChampion(Long leagueId) {
        Optional<League> league = leagueRepository.findById(leagueId);

        if (league.isEmpty()) {
            throw new RuntimeException("League not found");
        }

        League foundLeague = league.get();
        Participant champion = null;
        long maxWins = 0;

        for (Participant participant : foundLeague.getParticipants()) {
            long wins = matchRepository.countByWinnerAndLeagueId(participant, leagueId);
            if (wins > maxWins) {
                maxWins = wins;
                champion = participant;
            }
        }

        return champion;
    }
}
