CREATE TABLE IF NOT EXISTS hit
(
    id        BIGINT GENERATED BY DEFAULT AS IDENTITY
        PRIMARY KEY,
    app       VARCHAR(255),
    ip        INET,
    timestamp TIMESTAMP,
    uri       VARCHAR(1024)
);