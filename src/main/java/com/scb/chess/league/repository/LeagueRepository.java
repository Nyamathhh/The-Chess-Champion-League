package com.scb.chess.league.repository;

import com.scb.chess.league.model.League;
import com.scb.chess.league.model.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LeagueRepository extends JpaRepository<League, Long> {
    Optional<League> findByName(String name);

}
