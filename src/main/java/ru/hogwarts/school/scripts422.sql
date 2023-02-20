CREATE TABLE Person
(
    id   bigint primary key,
    name varchar not null,
    age integer,
    driver_license boolean,
    car_id integer[] references cars(id)
);

CREATE TABLE cars
(
    id   bigint primary key,
    brand varchar,
    model varchar,
    cost integer,
    driver integer references Person(id)
);