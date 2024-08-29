package com.scb.chess.league.controller;

import com.scb.chess.league.dto.LeagueDTO;
import com.scb.chess.league.model.League;
import com.scb.chess.league.service.LeagueService;
import com.scb.chess.league.utils.JSONUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/leagues")
public class LeagueController {

    private final LeagueService leagueService;

    @Autowired
    public LeagueController(LeagueService leagueService) {
        this.leagueService = leagueService;
    }

    @GetMapping
    public ResponseEntity<List<LeagueDTO>> getAllLeagues() {
        List<LeagueDTO> leagueDTOs = leagueService.getAllLeagues().stream()
                .map(league -> LeagueDTO.builder()
                        .id(league.getId())
                        .name(league.getName())
                        .build())
                .collect(Collectors.toList());
        return ResponseEntity.ok(leagueDTOs);
    }

    @PostMapping
    public ResponseEntity<LeagueDTO> createLeague(@RequestBody LeagueDTO leagueDTO) {
        League league = League.builder()
                .name(leagueDTO.getName())
                .build();
        League createdLeague = leagueService.saveLeague(league);

        LeagueDTO createdLeagueDTO = LeagueDTO.builder()
                .id(createdLeague.getId())
                .name(createdLeague.getName())
                .build();return ResponseEntity.status(HttpStatus.CREATED).body(createdLeagueDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LeagueDTO> getLeagueById(@PathVariable Long id) {
        return leagueService.getLeagueById(id)
                .map(league -> LeagueDTO.builder()
                        .id(league.getId())
                        .name(league.getName())
                        .build())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }
}
