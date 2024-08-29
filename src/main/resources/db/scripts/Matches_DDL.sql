CREATE TABLE matches (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         participant1_id BIGINT,
                         participant2_id BIGINT,
                         winner_id BIGINT,
                         league_id BIGINT,
                         match_date DATETIME,
                         FOREIGN KEY (participant1_id) REFERENCES participants(id),
                         FOREIGN KEY (participant2_id) REFERENCES participants(id),
                         FOREIGN KEY (winner_id) REFERENCES participants(id),
                         FOREIGN KEY (league_id) REFERENCES leagues(id)
);
