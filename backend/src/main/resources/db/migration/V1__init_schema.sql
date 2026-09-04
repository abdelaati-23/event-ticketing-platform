-- V1__init_schema.sql
CREATE TABLE users (
                       id UUID PRIMARY KEY,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       role VARCHAR(50) NOT NULL,
                       created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE events (
                        id UUID PRIMARY KEY,
                        title VARCHAR(255) NOT NULL,
                        description TEXT,
                        event_date TIMESTAMP NOT NULL,
                        location VARCHAR(255) NOT NULL,
                        total_seats INTEGER NOT NULL,
                        available_seats INTEGER NOT NULL,
                        organizer_id UUID NOT NULL REFERENCES users(id),
                        created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE tickets (
                         id UUID PRIMARY KEY,
                         event_id UUID NOT NULL REFERENCES events(id),
                         user_id UUID NOT NULL REFERENCES users(id),
                         price DECIMAL(10, 2) NOT NULL,
                         status VARCHAR(50) NOT NULL, -- States: RESERVED, PAID, CANCELLED
                         created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);