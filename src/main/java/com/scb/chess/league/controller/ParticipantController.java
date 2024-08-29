package com.scb.chess.league.controller;

import com.scb.chess.league.dto.ParticipantDTO;
import com.scb.chess.league.model.Participant;
import com.scb.chess.league.service.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/participants")
public class ParticipantController {

    private final ParticipantService participantService;

    @Autowired
    public ParticipantController(ParticipantService participantService) {
        this.participantService = participantService;
    }

    @GetMapping
    public ResponseEntity<List<ParticipantDTO>> getAllParticipants() {
        List<ParticipantDTO> participantDTOs = participantService.getAllParticipants().stream()
                .map(participant -> ParticipantDTO.builder()
                        .id(participant.getId())
                        .name(participant.getName())
                        .email(participant.getEmail()) // Assuming Participant has an email field
                        .build())
                .collect(Collectors.toList());
        return ResponseEntity.ok(participantDTOs);
    }

    @PostMapping
    public ResponseEntity<ParticipantDTO> createParticipant(@RequestBody ParticipantDTO participantDTO) {
        Participant participant = participantService.saveParticipant(participantDTO);
        ParticipantDTO createdParticipantDTO = ParticipantDTO.builder()
                .id(participant.getId())
                .name(participant.getName())
                .email(participant.getEmail())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(createdParticipantDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParticipantDTO> getParticipantById(@PathVariable Long id) {
        return participantService.getParticipantById(id)
                .map(participant -> ResponseEntity.ok(ParticipantDTO.builder()
                        .id(participant.getId())
                        .name(participant.getName())
                        .email(participant.getEmail())
                        .build()))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }
}
