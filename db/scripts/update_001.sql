CREATE TABLE post (
   id SERIAL PRIMARY KEY,
   name TEXT,
   description TEXT,
   created timestamp,
   city_id int references cities(id)
);

CREATE TABLE cities
(
    id   SERIAL PRIMARY KEY,
    name TEXT
);

insert into post (name, city_id) values ('Java', 1);
insert into post (name, city_id) values ('Puthon', 2);
insert into post (name, city_id) values ('Cobol', 1);

insert into cities (name) values ('Москва');
insert into cities (name) values ('Питер');


select * from cities, post where cities.id = post.city_id and post.id = 2


