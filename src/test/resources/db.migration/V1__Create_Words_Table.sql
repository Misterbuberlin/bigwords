CREATE TABLE cgm.words
(
    id   BIGSERIAL PRIMARY KEY,
    word VARCHAR(255) UNIQUE NOT NULL, --make word UNIQUE
    is_premium BOOLEAN -- premium or not
);
