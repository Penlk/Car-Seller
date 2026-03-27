CREATE TYPE fuel_type AS ENUM ('PETROL', 'DIESEL', 'ELECTRICITY');

CREATE TYPE gear_shift_box_type AS ENUM ('MANUAL', 'AUTOMATIC');

CREATE TYPE car_drive_type AS ENUM ('FRONT', 'REAR', 'FULL');

CREATE TABLE IF NOT EXISTS cars (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    price MONEY NOT NULL,
    brand TEXT,
    model TEXT,
    body TEXT,
    fuel fuel_type NOT NULL,
    engine_power FLOAT(53) NOT NULL,
    engine_volume FLOAT(53) NOT NULL,
    gear_shift_box gear_shift_box_type NOT NULL,
    car_drive car_drive_type NOT NULL,
    colour TEXT NOT NULL
)

CREATE TABLE IF NOT EXISTS require_nodes (
    node_id BIGINT REFERENCES nodes(id),
    car_id BIGINT REFERENCES cars(id),
    PRIMARY (node_id, car_id)
)