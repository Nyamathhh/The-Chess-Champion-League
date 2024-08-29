package com.scb.chess.league.controller;

import com.scb.chess.league.dto.MatchDTO;
import com.scb.chess.league.model.Match;
import com.scb.chess.league.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/matches")
public class MatchController {

    private final MatchService matchService;

    @Autowired
    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping
    public ResponseEntity<List<MatchDTO>> getAllMatches() {
        List<MatchDTO> matchDTOs = matchService.getAllMatches().stream()
                .map(match -> MatchDTO.builder()
                        .id(match.getId())
                        .participant1Name(match.getParticipant1().getName())
                        .participant2Name(match.getParticipant2().getName())
                        .winnerName(match.getWinner().getName())
                        .matchDate(match.getMatchDate())
                        .leagueName(match.getLeague().getName())
                        .build())
                .collect(Collectors.toList());
        return ResponseEntity.ok(matchDTOs);
    }

    @PostMapping
    public ResponseEntity<MatchDTO> createMatch(@RequestBody MatchDTO matchDTO) {
        if (matchDTO.getParticipant1Name().equals(matchDTO.getParticipant2Name())) {
            throw new IllegalArgumentException("Participant 1 and Participant 2 cannot be the same.");
        }

        Match match = matchService.saveMatch(matchDTO);
        MatchDTO createdMatchDTO = MatchDTO.builder()
                .id(match.getId())
                .participant1Name(match.getParticipant1().getName())
                .participant2Name(match.getParticipant2().getName())
                .winnerName(match.getWinner().getName())
                .matchDate(match.getMatchDate())
                .leagueName(match.getLeague().getName())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMatchDTO);
    }


    @PutMapping("/{id}")
    public ResponseEntity<MatchDTO> updateMatch(@PathVariable Long id, @RequestBody MatchDTO matchDTO) {
        if (matchDTO.getParticipant1Name().equals(matchDTO.getParticipant2Name())) {
            throw new IllegalArgumentException("Participant 1 and Participant 2 cannot be the same.");
        }
        if(!matchDTO.getWinnerName().equals(matchDTO.getParticipant1Name()) &&
        !matchDTO.getWinnerName().equals(matchDTO.getParticipant2Name())) {
            throw new IllegalArgumentException("Winner should be either Participant 1 or Participant 2");
        }
        Match updatedMatch = matchService.updateMatch(id, matchDTO);
        MatchDTO updatedMatchDTO = MatchDTO.builder()
                .id(updatedMatch.getId())
                .participant1Name(updatedMatch.getParticipant1().getName())
                .participant2Name(updatedMatch.getParticipant2().getName())
                .winnerName(updatedMatch.getWinner().getName())
                .matchDate(updatedMatch.getMatchDate())
                .leagueName(updatedMatch.getLeague().getName())
                .build();
        return ResponseEntity.ok(updatedMatchDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMatch(@PathVariable Long id) {
        matchService.deleteMatch(id);
        return ResponseEntity.noContent().build();
    }

}
