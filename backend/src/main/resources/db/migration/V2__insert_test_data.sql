-- 1. Realistic Users (Passwords are BCrypt hashes for 'password123')
INSERT INTO users (id, email, password, role) VALUES
                                                  ('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'alice.dupont@example.com', '$2a$10$wK1k6x6g3.3P0FvX3d0J3.1k1F4y5G6h7J8k9L0z1x2c3v4b5n6m.', 'CUSTOMER'),
                                                  ('b1eebc99-9c0b-4ef8-bb6d-6bb9bd380a22', 'marc.laurent@example.com', '$2a$10$wK1k6x6g3.3P0FvX3d0J3.1k1F4y5G6h7J8k9L0z1x2c3v4b5n6m.', 'CUSTOMER'),
                                                  ('c2eebc99-9c0b-4ef8-bb6d-6bb9bd380a33', 'sophie.martin@example.com', '$2a$10$wK1k6x6g3.3P0FvX3d0J3.1k1F4y5G6h7J8k9L0z1x2c3v4b5n6m.', 'CUSTOMER'),
                                                  ('d3eebc99-9c0b-4ef8-bb6d-6bb9bd380a44', 'admin.event@ticketing.com', '$2a$10$wK1k6x6g3.3P0FvX3d0J3.1k1F4y5G6h7J8k9L0z1x2c3v4b5n6m.', 'ADMIN')
    ON CONFLICT (id) DO NOTHING;

-- 2. Realistic Events Across Categories and Availabilities
INSERT INTO events (id, title, available_seats) VALUES
                                                    -- High-capacity stadium concert (Plenty of seats)
                                                    ('11111111-1111-1111-1111-111111111111', 'Coldplay - Music of the Spheres Tour (Stade de France)', 1500),

                                                    -- Sold-out festival (Crucial for testing the AI agent's sold-out handling)
                                                    ('22222222-2222-2222-2222-222222222222', 'Tomorrowland 2026 - Weekend 1 Pass', 0),

                                                    -- Tech Conference (Moderate availability)
                                                    ('33333333-3333-3333-3333-333333333333', 'Devoxx France 2026 - 3-Day Conference Pass', 85),

                                                    -- Classical/Film Score Concert (Nearly sold out)
                                                    ('44444444-4444-4444-4444-444444444444', 'Hans Zimmer Live - European Tour (Lille Zenith)', 4),

                                                    -- Stand-up comedy (Small venue)
                                                    ('55555555-5555-5555-5555-555555555555', 'Paul Mirabel - Par Amour (Paris Olympia)', 35)
    ON CONFLICT (id) DO NOTHING;

-- 3. Pre-existing Tickets (Gives your AI booking history to look up)
INSERT INTO tickets (id, event_id, user_id, price, status) VALUES
                                                               -- Alice already bought a Devoxx and Coldplay ticket
                                                               ('aaaa0000-0000-0000-0000-000000000001', '33333333-3333-3333-3333-333333333333', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 280.00, 'PAID'),
                                                               ('aaaa0000-0000-0000-0000-000000000002', '11111111-1111-1111-1111-111111111111', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 85.50, 'PAID'),

                                                               -- Marc has one of the sold-out Tomorrowland passes
                                                               ('bbbb0000-0000-0000-0000-000000000001', '22222222-2222-2222-2222-222222222222', 'b1eebc99-9c0b-4ef8-bb6d-6bb9bd380a22', 350.00, 'PAID')
    ON CONFLICT (id) DO NOTHING;