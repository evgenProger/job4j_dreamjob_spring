CREATE TABLE IF NOT EXISTS cities
(
    id   SERIAL PRIMARY KEY,
    name TEXT
);

CREATE TABLE IF NOT EXISTS post (
   id SERIAL PRIMARY KEY,
   name TEXT,
   description TEXT,
   created timestamp,
   city_id int references cities(id)
);



CREATE TABLE IF NOT EXISTS candidate (
    id SERIAL PRIMARY KEY,
    name TEXT,
    description TEXT,
    created timestamp
);

CREATE TABLE IF NOT EXISTS users (
   id SERIAL PRIMARY KEY,
   email varchar(25),
   password TEXT
);

ALTER TABLE users ADD CONSTRAINT email_unique UNIQUE (email)











