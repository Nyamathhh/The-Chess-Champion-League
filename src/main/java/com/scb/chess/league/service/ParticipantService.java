package com.scb.chess.league.service;

import com.scb.chess.league.dto.ParticipantDTO;
import com.scb.chess.league.model.Participant;
import com.scb.chess.league.repository.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParticipantService {

    @Autowired
    private ParticipantRepository participantRepository;

    public List<Participant> getAllParticipants() {
        return participantRepository.findAll();
    }

    public Participant saveParticipant(ParticipantDTO participantDTO) {
        Participant participant = new Participant();
        participant.setName(participantDTO.getName());
        participant.setEmail(participantDTO.getEmail());
        return participantRepository.save(participant);
    }

    public Optional<Participant> getParticipantById(Long id) {
        return participantRepository.findById(id);
    }
}
