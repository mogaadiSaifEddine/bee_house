-- Create database if not exists
CREATE DATABASE IF NOT EXISTS bee_house;
USE bee_house;

-- Create users table
CREATE TABLE IF NOT EXISTS users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    full_name VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL,
    active BOOLEAN DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Create farms table
CREATE TABLE IF NOT EXISTS farms (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    location VARCHAR(255) NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Create sites table
CREATE TABLE IF NOT EXISTS sites (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    farm_id BIGINT NOT NULL,
    name VARCHAR(100) NOT NULL,
    location VARCHAR(255) NOT NULL,
    description TEXT,
    coordinates VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (farm_id) REFERENCES farms(id) ON DELETE CASCADE
);

-- Create hives table
CREATE TABLE IF NOT EXISTS hives (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    site_id BIGINT NOT NULL,
    name VARCHAR(100) NOT NULL,
    type VARCHAR(50) NOT NULL,
    status VARCHAR(50) NOT NULL,
    queen_status VARCHAR(50),
    last_inspection_date DATE,
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (site_id) REFERENCES sites(id) ON DELETE CASCADE
);

-- Create visits table
CREATE TABLE IF NOT EXISTS visits (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    hive_id BIGINT NOT NULL,
    visit_date DATE NOT NULL,
    inspector VARCHAR(100) NOT NULL,
    weather_conditions VARCHAR(100),
    queen_seen BOOLEAN,
    brood_present BOOLEAN,
    honey_stores VARCHAR(50),
    pest_presence VARCHAR(50),
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (hive_id) REFERENCES hives(id) ON DELETE CASCADE
);

-- Create honey_production table
CREATE TABLE IF NOT EXISTS honey_production (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    hive_id BIGINT NOT NULL,
    harvest_date DATE NOT NULL,
    quantity DECIMAL(10,2) NOT NULL,
    quality_grade VARCHAR(50),
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (hive_id) REFERENCES hives(id) ON DELETE CASCADE
);

-- Insert dummy users (password is 'password123' hashed)
INSERT INTO users (username, password, email, full_name, role, active) VALUES
('admin', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'admin@beehouse.com', 'Admin User', 'ADMIN', true),
('john.doe', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'john.doe@example.com', 'John Doe', 'FARMER', true),
('jane.smith', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'jane.smith@example.com', 'Jane Smith', 'AGENT', true);

-- Insert dummy farms
INSERT INTO farms (name, location, description) VALUES
('Sunny Valley Apiary', '123 Valley Road, Honeyville', 'Main beekeeping location with diverse flora'),
('Mountain View Farm', '456 Hill Street, Mountainville', 'High altitude apiary with wildflowers'),
('Riverside Bees', '789 River Road, Waterside', 'Riverside location with abundant water sources');

-- Insert dummy sites
INSERT INTO sites (farm_id, name, location, description, coordinates) VALUES
(1, 'North Field', 'North section of Sunny Valley', 'Sunny location with good forage', '45.1234, -122.5678'),
(1, 'South Field', 'South section of Sunny Valley', 'Protected area with water source', '45.1235, -122.5679'),
(2, 'Mountain Meadow', 'Main mountain location', 'High altitude meadow with wildflowers', '45.2345, -122.6789'),
(3, 'River Bank', 'Riverside location', 'Close to water source with good forage', '45.3456, -122.7890');

-- Insert dummy hives
INSERT INTO hives (site_id, name, type, status, queen_status, last_inspection_date) VALUES
(1, 'Hive-001', 'Langstroth', 'Active', 'Present', CURRENT_DATE),
(1, 'Hive-002', 'Langstroth', 'Active', 'Present', CURRENT_DATE),
(2, 'Hive-003', 'Top Bar', 'Active', 'Present', CURRENT_DATE),
(3, 'Hive-004', 'Langstroth', 'Active', 'Present', CURRENT_DATE),
(4, 'Hive-005', 'Langstroth', 'Active', 'Present', CURRENT_DATE);

-- Insert dummy visits
INSERT INTO visits (hive_id, visit_date, inspector, weather_conditions, queen_seen, brood_present, honey_stores, pest_presence, notes) VALUES
(1, CURRENT_DATE, 'jane.smith', 'Sunny, 25°C', true, true, 'Good', 'None', 'Hive is healthy and productive'),
(2, CURRENT_DATE, 'jane.smith', 'Sunny, 25°C', true, true, 'Medium', 'None', 'Regular maintenance needed'),
(3, CURRENT_DATE, 'jane.smith', 'Cloudy, 20°C', true, true, 'Good', 'None', 'Hive is doing well'),
(4, CURRENT_DATE, 'jane.smith', 'Rainy, 18°C', true, true, 'Low', 'None', 'Monitor honey stores');

-- Insert dummy honey production
INSERT INTO honey_production (hive_id, harvest_date, quantity, quality_grade, notes) VALUES
(1, DATE_SUB(CURRENT_DATE, INTERVAL 30 DAY), 25.5, 'Premium', 'Excellent quality honey'),
(2, DATE_SUB(CURRENT_DATE, INTERVAL 30 DAY), 20.0, 'Standard', 'Good quality honey'),
(3, DATE_SUB(CURRENT_DATE, INTERVAL 30 DAY), 22.5, 'Premium', 'High-quality mountain honey'),
(4, DATE_SUB(CURRENT_DATE, INTERVAL 30 DAY), 18.0, 'Standard', 'Regular quality honey'); 