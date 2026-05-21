CREATE TABLE IF NOT EXISTS users (
                                     id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     email VARCHAR(255) NOT NULL UNIQUE,
    name VARCHAR(255),
    provider VARCHAR(100),
    created_at TIMESTAMP,
    updated_at TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS user_roles (
                                          user_id BIGINT NOT NULL,
                                          role VARCHAR(100),
    CONSTRAINT fk_user_roles_user FOREIGN KEY (user_id) REFERENCES users(id)
    );

CREATE TABLE IF NOT EXISTS quantity_measurements (
                                                     id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                                     measurement_type VARCHAR(50),
    operation VARCHAR(50),
    input VARCHAR(1024),
    result VARCHAR(1024),
    owner_email VARCHAR(255),
    timestamp TIMESTAMP
    );

CREATE INDEX idx_measurement_type ON quantity_measurements(measurement_type);
CREATE INDEX idx_operation ON quantity_measurements(operation);
CREATE INDEX idx_owner_email ON quantity_measurements(owner_email);
