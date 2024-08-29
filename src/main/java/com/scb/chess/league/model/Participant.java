package com.scb.chess.league.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
@Table(name = "participants")
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "participants_seq")
    @SequenceGenerator(name = "participants_seq", sequenceName = "PARTICIPANTS_SEQ", allocationSize = 1)
    private Long id;

    @NotBlank
    private String name;

    private String email;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "league_id")
    private League league;
}
