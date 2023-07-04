-- db/migration/V1__create_table.sql

CREATE SCHEMA IF NOT EXISTS cgm;

CREATE TABLE IF NOT EXISTS cgm.words (
                                                      id INT PRIMARY KEY,
                                                      word VARCHAR(255) NOT NULL,
                                                      is_premium boolean NOT NULL
);
