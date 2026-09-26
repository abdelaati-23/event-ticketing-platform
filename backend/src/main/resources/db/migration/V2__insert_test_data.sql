-- ============================================================
-- V2__seed_test_data.sql
-- Database Seed: Users, Real-World Events & Pre-Existing Tickets
-- ============================================================

-- ------------------------------------------------------------
-- 1. USERS
-- Default password for all users: "password123"
-- ------------------------------------------------------------
INSERT INTO users (id, email, password, role, created_at)
VALUES
    -- Organizer Account (References events.organizer_id)
    (
        '11111111-1111-1111-1111-111111111111',
        'organizer@ticketing.com',
        '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG',
        'ADMIN',
        NOW()
    ),
    -- Customer: Alice Dupont (Has active bookings)
    (
        'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11',
        'alice.dupont@example.com',
        '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG',
        'CUSTOMER',
        NOW()
    ),
    -- Customer: Marc Laurent (Has festival pass)
    (
        'b1eebc99-9c0b-4ef8-bb6d-6bb9bd380a22',
        'marc.laurent@example.com',
        '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG',
        'CUSTOMER',
        NOW()
    ),
    -- Customer: Sophie Martin (Fresh account with no bookings)
    (
        'c2eebc99-9c0b-4ef8-bb6d-6bb9bd380a33',
        'sophie.martin@example.com',
        '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG',
        'CUSTOMER',
        NOW()
    ),
    -- Platform Admin
    (
        'd3eebc99-9c0b-4ef8-bb6d-6bb9bd380a44',
        'admin.event@ticketing.com',
        '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG',
        'ADMIN',
        NOW()
    )
    ON CONFLICT (id) DO UPDATE
                            SET password = EXCLUDED.password;


-- ------------------------------------------------------------
-- 2. EVENTS
-- Covers stadium shows, conferences, small venues, and sold-out edge cases
-- ------------------------------------------------------------
INSERT INTO events (
    id,
    title,
    description,
    event_date,
    location,
    total_seats,
    available_seats,
    organizer_id,
    created_at
)
VALUES
    -- Stadium Concert (High capacity)
    (
        '10000000-0000-0000-0000-000000000001',
        'Coldplay - Music of the Spheres Tour',
        'Global stadium tour featuring classic hits and light shows.',
        '2026-11-15 20:30:00',
        'Stade de France, Paris',
        80000,
        1540,
        '11111111-1111-1111-1111-111111111111',
        NOW()
    ),
    -- Tech Conference
    (
        '33333333-3333-3333-3333-333333333333',
        'Devoxx France 2026 - 3-Day Conference Pass',
        'Premier developer conference covering Java 25, Spring Boot, and AI architectures.',
        '2026-11-04 09:00:00',
        'Palais des Congres, Paris',
        3000,
        85,
        '11111111-1111-1111-1111-111111111111',
        NOW()
    ),
    -- Film Score Live Concert (Nearly sold out edge case)
    (
        '44444444-4444-4444-4444-444444444444',
        'Hans Zimmer Live - European Tour',
        'Symphonic orchestra performing iconic movie themes.',
        '2026-11-28 20:00:00',
        'Zenith de Lille, Lille',
        7000,
        4,
        '11111111-1111-1111-1111-111111111111',
        NOW()
    ),
    -- Stand-up Comedy (Small intimate venue)
    (
        '55555555-5555-5555-5555-555555555555',
        'Paul Mirabel - Par Amour',
        'Stand-up comedy performance live in Paris.',
        '2026-12-10 20:00:00',
        'Olympia Bruno Coquatrix, Paris',
        2000,
        35,
        '11111111-1111-1111-1111-111111111111',
        NOW()
    ),
    -- Sold-Out Festival (Critical for testing 0-seat reasoning)
    (
        '22222222-2222-2222-2222-222222222222',
        'Tomorrowland 2026 - Full Madness Pass',
        'Electronic dance music festival.',
        '2026-10-10 12:00:00',
        'Boom, Belgium',
        60000,
        0,
        '11111111-1111-1111-1111-111111111111',
        NOW()
    )
    ON CONFLICT (id) DO UPDATE
                            SET available_seats = EXCLUDED.available_seats,
                            title = EXCLUDED.title,
                            location = EXCLUDED.location;


-- ------------------------------------------------------------
-- 3. TICKETS (Historical bookings)
-- ------------------------------------------------------------
INSERT INTO tickets (id, event_id, user_id, price, status)
VALUES
    -- Alice bought a Devoxx conference pass
    (
        'aaaa0000-0000-0000-0000-000000000001',
        '33333333-3333-3333-3333-333333333333',
        'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11',
        280.00,
        'PAID'
    ),
    -- Alice bought a Coldplay stadium ticket
    (
        'aaaa0000-0000-0000-0000-000000000002',
        '10000000-0000-0000-0000-000000000001',
        'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11',
        85.50,
        'PAID'
    ),
    -- Marc owns one of the sold-out Tomorrowland passes
    (
        'bbbb0000-0000-0000-0000-000000000001',
        '22222222-2222-2222-2222-222222222222',
        'b1eebc99-9c0b-4ef8-bb6d-6bb9bd380a22',
        350.00,
        'PAID'
    )
    ON CONFLICT (id) DO NOTHING;