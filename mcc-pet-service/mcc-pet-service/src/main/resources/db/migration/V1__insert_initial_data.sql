-- ==============================
-- V1__insert_initial_data.sql
-- ==============================

-- Insert pets with owner references
INSERT INTO pets (name, race, age, owner_id) VALUES
('Buddy', 'Golden Retriever', 3, 1),
('Milo', 'Beagle', 2, 2),
('Luna', 'Labrador', 4, 3),
('Charlie', 'Bulldog', 1, 4),
('Bella', 'Poodle', 5, 5),
('Max', 'German Shepherd', 2, 6),
('Lucy', 'Dachshund', 3, 7),
('Rocky', 'Boxer', 4, 8),
('Daisy', 'Chihuahua', 1, 9),
('Bailey', 'Shih Tzu', 2, 10);
