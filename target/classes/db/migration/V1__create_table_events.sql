CREATE TABLE events (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    type VARCHAR(50) NOT NULL,
    time_event VARCHAR(200) NOT NULL,
    number_of_person INTEGER NOT NULL,
    localization VARCHAR(50) NOT NULL
);