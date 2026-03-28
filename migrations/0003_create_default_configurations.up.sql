CREATE TABLE IF NOT EXISTS default_configurations (
    car_id BIGINT REFERENCES cars(id),
    car_part_id BIGINT REFERENCES car_parts(id),
    PRIMARY KEY (car_id, car_part_id)
);