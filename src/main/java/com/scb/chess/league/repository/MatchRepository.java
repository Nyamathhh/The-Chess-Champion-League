package com.scb.chess.league.repository;

import com.scb.chess.league.model.Match;
import com.scb.chess.league.model.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<Match, Long> {
    long countByWinnerAndLeagueId(Participant winner, Long leagueId);
    long countByWinner(Participant winner);
}

