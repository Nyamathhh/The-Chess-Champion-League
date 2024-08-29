package com.scb.chess.league.service;

import com.scb.chess.league.dto.MatchDTO;
import com.scb.chess.league.model.Match;
import com.scb.chess.league.repository.LeagueRepository;
import com.scb.chess.league.repository.MatchRepository;
import com.scb.chess.league.repository.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class MatchService {

    private final MatchRepository matchRepository;
    private final ParticipantRepository participantRepository;
    private final LeagueRepository leagueRepository;

    @Autowired
    public MatchService(MatchRepository matchRepository, ParticipantRepository participantRepository, LeagueRepository leagueRepository) {
        this.matchRepository = matchRepository;
        this.participantRepository = participantRepository;
        this.leagueRepository = leagueRepository;
    }

    public List<Match> getAllMatches() {
        return matchRepository.findAll();
    }

    public Match saveMatch(MatchDTO matchDTO) {
        Match match = new Match();
        match.setParticipant1(participantRepository.findByName(matchDTO.getParticipant1Name()).orElseThrow());
        match.setParticipant2(participantRepository.findByName(matchDTO.getParticipant2Name()).orElseThrow());
        match.setWinner(participantRepository.findByName(matchDTO.getWinnerName()).orElseThrow());
        match.setLeague(leagueRepository.findByName(matchDTO.getLeagueName()).orElseThrow());
        match.setMatchDate(matchDTO.getMatchDate());
        return matchRepository.save(match);
    }

    public Match updateMatch(Long id, MatchDTO matchDTO) {
        Match match = matchRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No match found with id " + id));

        match.setParticipant1(participantRepository.findByName(matchDTO.getParticipant1Name())
                .orElseThrow(() -> new NoSuchElementException("No participant found with name " + matchDTO.getParticipant1Name())));

        match.setParticipant2(participantRepository.findByName(matchDTO.getParticipant2Name())
                .orElseThrow(() -> new NoSuchElementException("No participant found with name " + matchDTO.getParticipant2Name())));

        match.setWinner(participantRepository.findByName(matchDTO.getWinnerName())
                .orElseThrow(() -> new NoSuchElementException("No participant found with name " + matchDTO.getWinnerName())));

        match.setLeague(leagueRepository.findByName(matchDTO.getLeagueName())
                .orElseThrow(() -> new NoSuchElementException("No league found with name " + matchDTO.getLeagueName())));

        match.setMatchDate(matchDTO.getMatchDate());

        return matchRepository.save(match);
    }

    public void deleteMatch(Long id) {
        matchRepository.deleteById(id);
    }
}
