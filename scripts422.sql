-- Описание структуры: у каждого человека есть машина.
--     Причем несколько человек могут пользоваться одной машиной.
--     У каждого человека есть имя, возраст и признак того, что у него есть права (или их нет).
--     У каждой машины есть марка, модель и стоимость.
--     Также не забудьте добавить таблицам первичные ключи и связать их.

CREATE TABLE drivers
(
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    age INTEGER CHECK ( age > 0 ),
    license BOOLEAN SET DEFAULT (0),
    car_id SERIAL REFERENCES cars (id)
);

CREATE TABLE cars
(
    id SERIAL PRIMARY KEY,
    brand TEXT NOT NULL,
    model TEXT NOT NULL,
    cost MONEY CHECK ( cost > 0 )
);