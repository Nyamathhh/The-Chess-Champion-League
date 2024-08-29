/**
 * 
 */
package com.scb.chess.league.swagger;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConditionalOnProperty(prefix = "swagger", name = "enable", havingValue = "true")
public class SwaggerConfiguration {

	@Bean
	public GroupedOpenApi chessLeagueAPIs() {
		return GroupedOpenApi.builder()
				.group("Chess League APIs")
				.packagesToScan("com.scb.chess.league.controller")
				.build();
	}

}
