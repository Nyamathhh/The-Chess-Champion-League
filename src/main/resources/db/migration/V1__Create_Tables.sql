CREATE TABLE leagues (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(255) NOT NULL
);

CREATE TABLE participants (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              name VARCHAR(255) NOT NULL,
                              email VARCHAR(255),
                              league_id BIGINT,
                              FOREIGN KEY (league_id) REFERENCES leagues(id)
);

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

CREATE SEQUENCE LEAGUES_SEQ START WITH 1000 INCREMENT BY 1;
CREATE SEQUENCE MATCHES_SEQ START WITH 1000 INCREMENT BY 1;
CREATE SEQUENCE PARTICIPANTS_SEQ START WITH 1000 INCREMENT BY 1;
