CREATE TABLE IF NOT EXISTS car (
    id BIGSERIAL PRIMARY KEY,
    in_the_garage BOOLEAN NOT NULL,
    year INTEGER NOT NULL,
    color VARCHAR(255),
    license_plate VARCHAR(255) UNIQUE,
    make VARCHAR(255),
    model VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS garage (
   id BIGSERIAL PRIMARY KEY,
   base_price DOUBLE PRECISION NOT NULL,
   close_hour TIME,
   duration_limit_minutes INTEGER NOT NULL,
   max_capacity INTEGER NOT NULL,
   open_hour TIME,
   sector SMALLINT CHECK (sector BETWEEN 0 AND 1)
);


CREATE TABLE IF NOT EXISTS spot (
   id BIGSERIAL PRIMARY KEY,
   lat DOUBLE PRECISION NOT NULL,
   lng DOUBLE PRECISION NOT NULL,
   sector SMALLINT CHECK (sector BETWEEN 0 AND 1)
);