CREATE TYPE special_order_state AS ENUM (
    'ISSUED',
    'AGREED',
    'WAITING_PAYMENT',
    'PAID',
    'CAR_IS_READY',
    'CANCELED',
    'DONE'
);


CREATE TABLE IF NOT EXISTS special_orders (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    state special_order_state NOT NULL,
    client_id BIGINT REFERENCES clients(id),
    manager_id BIGINT REFERENCES managers(id),
    car_id BIGINT REFERENCES cars(id) NOT NULL,
    price MONEY NOT NULL
);

CREATE TABLE IF NOT EXISTS special_configurations (
    car_id BIGINT REFERENCES cars(id) NOT NULL,
    car_part_id BIGINT REFERENCES car_parts(id) NOT NULL,
    price MONEY NOT NULL,
    PRIMARY KEY (car_id, car_part_id)
);

CREATE TABLE IF NOT EXISTS node_sets (
    special_order_id BIGINT REFERENCES special_orders(id) NOT NULL,
    car_part_id BIGINT REFERENCES car_parts(id) NOT NULL,
    PRIMARY KEY (special_order_id, car_part_id)
);