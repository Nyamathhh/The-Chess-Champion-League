package com.scb.chess.league.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@Table(name = "matches")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "matches_seq")
    @SequenceGenerator(name = "matches_seq", sequenceName = "MATCHES_SEQ", allocationSize = 1)
    private Long id;

    @ManyToOne
    private Participant participant1;

    @ManyToOne
    private Participant participant2;

    @ManyToOne
    private Participant winner;

    @ManyToOne
    private League league;

    private Date matchDate;

}