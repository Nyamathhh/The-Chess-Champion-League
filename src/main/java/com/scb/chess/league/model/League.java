package com.scb.chess.league.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "leagues")
public class League {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "leagues_seq")
    @SequenceGenerator(name = "leagues_seq", sequenceName = "LEAGUES_SEQ", allocationSize = 1)
    private Long id;

    @NotBlank
    private String name;

    @OneToMany(mappedBy = "league")
    private List<Participant> participants;

}