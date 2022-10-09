CREATE TABLE cities
(
    id   SERIAL PRIMARY KEY,
    name TEXT
);

CREATE TABLE post (
   id SERIAL PRIMARY KEY,
   name TEXT,
   description TEXT,
   created timestamp,
   city_id int references cities(id)
);



CREATE TABLE candidate (
    id SERIAL PRIMARY KEY,
    name TEXT,
    description TEXT,
    created timestamp
);

select post.id, post.name, post.description, post.created, city_id, cities.name from cities, post  where cities.id = post.city_id









