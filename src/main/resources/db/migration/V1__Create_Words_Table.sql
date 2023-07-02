--CREATE SCHEMA cgm;
CREATE TABLE cgm.words
(
    id   BIGSERIAL PRIMARY KEY,
    word VARCHAR(255) UNIQUE NOT NULL, --make word UNIQUE
    is_premium BOOLEAN DEFAULT FALSE-- premium or not
);

INSERT INTO cgm.words (word, is_premium) VALUES ('this', true);
INSERT INTO cgm.words (word, is_premium) VALUES ('is', true);
INSERT INTO cgm.words (word, is_premium) VALUES ('premium', true);
