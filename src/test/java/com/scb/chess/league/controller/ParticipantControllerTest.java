package com.scb.chess.league.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scb.chess.league.model.Participant;
import com.scb.chess.league.repository.ParticipantRepository;
import com.scb.chess.league.service.ParticipantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestPropertySource(properties = "spring.security.enabled=false")
@SpringBootTest
@AutoConfigureMockMvc
class ParticipantControllerTest {

    @Mock
    private ParticipantService participantService;

    @Mock
    private ParticipantRepository participantRepository;

    @Autowired
    private MockMvc mockMvc;

    private Participant participant;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        participant = new Participant();
        participant.setId(1L);
        participant.setName("Participant 1");
    }

    @Test
    void testGetAllParticipants() {
        when(participantService.getAllParticipants()).thenReturn(List.of(participant));
        List<Participant> response = participantService.getAllParticipants();
        assertEquals(1, response.size());
    }

    @Test
    void testGetParticipantById_Found() {
        when(participantService.getParticipantById(1L)).thenReturn(Optional.of(participant));

        Optional<Participant> response = participantService.getParticipantById(1L);

        assertEquals(false, response.isEmpty());
    }

    @Test
    void testGetParticipantById_NotFound() {
        when(participantService.getParticipantById(1L)).thenReturn(Optional.empty());

        Optional<Participant> response = participantService.getParticipantById(1L);

        assertEquals(true, response.isEmpty());
    }

    @Test
    void testCreateParticipant() throws Exception {
        when(participantRepository.save(any(Participant.class))).thenReturn(participant);

        mockMvc.perform(post("/api/v1/leagues")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(participant))
                        .with(csrf().asHeader()))
                .andExpect(status().isCreated());
    }

    private String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
