package com.scb.chess.league.controller;

import com.scb.chess.league.dto.ChampionDTO;
import com.scb.chess.league.model.Participant;
import com.scb.chess.league.repository.MatchRepository;
import com.scb.chess.league.service.ChampionService;
import com.scb.chess.league.service.EmailService;
import com.scb.chess.league.service.LeagueService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/champions")
public class ChampionController {

    private final ChampionService championService;

    private final MatchRepository matchRepository;
    private final EmailService emailService;
    private final LeagueService leagueService;

    @Autowired
    public ChampionController(ChampionService championService, EmailService emailService, LeagueService leagueService, MatchRepository matchRepository) {
        this.championService = championService;
        this.emailService = emailService;
        this.leagueService = leagueService;
        this.matchRepository = matchRepository;
    }

    @GetMapping("/current")
    public ResponseEntity<ChampionDTO> getCurrentChampion() {
        Optional<Participant> champion = championService.determineChampion();
        long totalWins = matchRepository.countByWinner(champion.get());
        ChampionDTO dto = new ChampionDTO();
        dto.setId(champion.get().getId());
        dto.setName(champion.get().getName());
        dto.setEmail(champion.get().getEmail());
        dto.setTotalWins(totalWins);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/notify")
    public ResponseEntity<String> notifyChampion() {
        Optional<Participant> champion = championService.determineChampion();
        if (champion.isPresent()) {
            try {
                emailService.sendCongratulationEmail(champion.get());
                return ResponseEntity.ok("Congratulation email sent to the champion.");
            } catch (MessagingException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Failed to send email: " + e.getMessage());
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Champion not found.");
    }

    @GetMapping("/leagues/{leagueId}/champion")
    public ResponseEntity<ChampionDTO> getLeagueChampion(@PathVariable Long leagueId) {
        Optional<Participant> champion = Optional.ofNullable(leagueService.determineLeagueChampion(leagueId));
        long totalWins = matchRepository.countByWinnerAndLeagueId(champion.get(), leagueId);
        ChampionDTO dto = new ChampionDTO();
        dto.setId(champion.get().getId());
        dto.setName(champion.get().getName());
        dto.setEmail(champion.get().getEmail());
        dto.setTotalWins(totalWins);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/leagues/{leagueId}/notify")
    public ResponseEntity<String> notifyLeagueChampion(@PathVariable Long leagueId) {
        Optional<Participant> champion = Optional.ofNullable(leagueService.determineLeagueChampion(leagueId));
        if (champion.isPresent()) {
            try {
                emailService.sendCongratulationEmail(champion.get());
                return ResponseEntity.ok("Congratulation email sent to the league champion.");
            } catch (MessagingException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Failed to send email: " + e.getMessage());
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("League Champion not found.");
    }
}
