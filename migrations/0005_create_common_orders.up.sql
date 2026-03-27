CREATE TYPE common_order_state AS ENUM (
    'ISSUED',
    'AGREED',
    'WAITING_PAYMENT',
    'PAID',
    'CAR_IS_READY',
    'CANCELED',
    'DONE'
);

CREATE TABLE IF NOT EXISTS common_orders (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    state common_order_state NOT NULL,
    client_id BIGINT REFERENCES clients(id),
    manager_id BIGINT REFERENCES managers(id),
    car_id BIGINT REFERENCES cars(id) NOT NULL
)