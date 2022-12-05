CREATE TABLE IF NOT EXISTS users
(
    id         BIGINT GENERATED BY DEFAULT AS IDENTITY
        PRIMARY KEY,
    created_on TIMESTAMP,
    email      VARCHAR(255) NOT NULL,
    name       VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS event_categories
(
    id         BIGINT GENERATED BY DEFAULT AS IDENTITY
        PRIMARY KEY,
    created_on TIMESTAMP,
    name       VARCHAR(255) UNIQUE
);

CREATE TABLE IF NOT EXISTS events
(
    id                 BIGINT GENERATED BY DEFAULT AS IDENTITY
        PRIMARY KEY,
    created_on         TIMESTAMP,
    annotation         VARCHAR(2000)    NOT NULL,
    description        VARCHAR(7000)    NOT NULL,
    event_date         TIMESTAMP        NOT NULL
        CONSTRAINT events_event_date_check
            CHECK (event_date > (CURRENT_TIMESTAMP + ((2)::DOUBLE PRECISION * '01:00:00'::INTERVAL))),
    lat                DOUBLE PRECISION NOT NULL,
    lon                DOUBLE PRECISION NOT NULL,
    paid               BOOLEAN          NOT NULL,
    participant_limit  INTEGER,
    published_on       TIMESTAMP,
    request_moderation BOOLEAN,
    state              INTEGER,
    title              VARCHAR(120)     NOT NULL,
    category_id        BIGINT           NOT NULL
        REFERENCES event_categories,
    initiator_id       BIGINT           NOT NULL
        REFERENCES users
);

CREATE TABLE IF NOT EXISTS event_compilations
(
    id         BIGINT GENERATED BY DEFAULT AS IDENTITY
        PRIMARY KEY,
    created_on TIMESTAMP,
    pinned     BOOLEAN,
    title      VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS event_compilation_event
(
    event_compilation_id BIGINT NOT NULL
            REFERENCES event_compilations,
    event_id             BIGINT NOT NULL
            REFERENCES events,
    PRIMARY KEY (event_compilation_id, event_id)
);

CREATE TABLE IF NOT EXISTS participation_requests
(
    id           BIGINT GENERATED BY DEFAULT AS IDENTITY
        PRIMARY KEY,
    created_on   TIMESTAMP,
    status       INTEGER,
    event_id     BIGINT NOT NULL REFERENCES events,
    requester_id BIGINT NOT NULL REFERENCES users
);

