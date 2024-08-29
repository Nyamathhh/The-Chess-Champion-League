package com.scb.chess.league.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChampionDTO {
    private Long id;
    private String name;
    private String email;
    private long totalWins;
}
