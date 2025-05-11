-- Create one database for all microservices
CREATE DATABASE pitchplease;

-- Connect to database
\c pitchplease
-- BEGIN;

-- Users table
CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL
);

-- Facilities table
CREATE TABLE facilities (
    facility_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    address VARCHAR(255) NOT NULL,
    city VARCHAR(50) NOT NULL,
    facility_type VARCHAR(50) NOT NULL, -- e.g., football field, tennis court, etc.
    hourly_rate DECIMAL(10, 2) NOT NULL,
    owner_id INT REFERENCES users(user_id)
);

-- Bookings table
CREATE TABLE bookings (
    booking_id SERIAL PRIMARY KEY,
    facility_id INT NOT NULL REFERENCES facilities(facility_id),
    user_id INT NOT NULL REFERENCES users(user_id),
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL,
    total_price DECIMAL(10, 2) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'pending', -- pending, confirmed, cancelled
    
    -- Enforce that start time is before end time
    CONSTRAINT valid_booking_times CHECK (start_time < end_time)
);

-- Create basic indexes
CREATE INDEX idx_bookings_facility_id ON bookings(facility_id);
CREATE INDEX idx_bookings_user_id ON bookings(user_id);


-- Populate users table
INSERT INTO users (username, email, password_hash) VALUES
('john_doe', 'john@example.com', '$2a$10$XQhF5XeGQWrffCcbJcxDVedoQILBKCPVSClOkCqsYc/cNnRZ9jNzy'), -- 'password123'
('jane_smith', 'jane@example.com', '$2a$10$J5kX1XeGQWrfdCcbJcxDVeiuQILBKCPVSClOkCqsYc/cNnRZ9jNzy'), -- 'securepass'
('sam_wilson', 'sam@example.com', '$2a$10$LQhF5XeGQWrffCcbJcxDVeBuQILBKCPVSClOkCqsYc/cNnRZ9jNzy'); -- 'samspass'

-- Populate facilities table
INSERT INTO facilities (name, description, address, city, facility_type, hourly_rate, owner_id) VALUES
('Central Football Field', 'Professional football field with floodlights', '123 Main St', 'New York', 'football', 100.00, 1),
('Downtown Tennis Court', 'Clay tennis court with amenities', '456 Park Ave', 'New York', 'tennis', 45.00, 1),
('Elite Basketball Arena', 'Indoor basketball court with seating', '789 Broadway', 'Boston', 'basketball', 80.00, 2),
('Riverside Soccer Field', 'Open-air soccer field near the river', '321 River Rd', 'Chicago', 'football', 75.00, 3),
('Community Swimming Pool', 'Olympic-sized swimming pool', '654 Ocean Dr', 'Miami', 'swimming', 60.00, 2);

-- Populate bookings table
INSERT INTO bookings (facility_id, user_id, start_time, end_time, total_price, status) VALUES
(1, 2, '2025-04-20 14:00:00', '2025-04-20 16:00:00', 200.00, 'confirmed'),
(2, 3, '2025-04-21 10:00:00', '2025-04-21 12:00:00', 90.00, 'confirmed'),
(3, 1, '2025-04-22 18:00:00', '2025-04-22 20:00:00', 160.00, 'pending'),
(4, 2, '2025-04-23 16:00:00', '2025-04-23 18:00:00', 150.00, 'confirmed'),
(5, 3, '2025-04-24 09:00:00', '2025-04-24 11:00:00', 120.00, 'pending'),
(1, 3, '2025-04-25 12:00:00', '2025-04-25 14:00:00', 200.00, 'cancelled');

-- COMMIT;