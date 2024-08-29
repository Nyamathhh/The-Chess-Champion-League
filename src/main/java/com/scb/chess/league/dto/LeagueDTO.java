package com.scb.chess.league.dto;

import com.scb.chess.league.model.Participant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeagueDTO {
    private Long id;
    private String name;
}