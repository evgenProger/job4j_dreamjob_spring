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
   name varchar(25),
   email  varchar(25) unique,
   password TEXT
);

insert into post (name) values ('java');
insert into post (description) values ('java');

select * from post where name like 'java' or description like  'java'









