-- V2__insert_test_data.sql

-- 1. Insert an Organizer (UUID starts with 1111...)
INSERT INTO users (id, email, password, role, created_at)
VALUES ('11111111-1111-1111-1111-111111111111', 'organizer@test.com', 'hashed_pwd', 'ORGANIZER', CURRENT_TIMESTAMP);

-- 2. Insert a Customer (UUID starts with 2222...)
INSERT INTO users (id, email, password, role, created_at)
VALUES ('22222222-2222-2222-2222-222222222222', 'customer@test.com', 'hashed_pwd', 'CUSTOMER', CURRENT_TIMESTAMP);

-- 3. Insert a Concert Event organized by the Organizer (UUID starts with 3333...)
-- Total seats: 100, Available: 100
INSERT INTO events (id, title, description, event_date, location, total_seats, available_seats, organizer_id, created_at)
VALUES ('33333333-3333-3333-3333-333333333333', 'Epic Summer Concert', 'Amazing live music event', '2026-12-31 20:00:00', 'Paris Arena', 100, 100, '11111111-1111-1111-1111-111111111111', CURRENT_TIMESTAMP);