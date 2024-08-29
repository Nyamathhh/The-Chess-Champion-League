package com.scb.chess.league.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class MatchDTO {
    private Long id;
    private String participant1Name;
    private String participant2Name;
    private String winnerName;
    private String leagueName;
    private Date matchDate;
}
