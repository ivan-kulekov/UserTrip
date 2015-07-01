-- Database: user_travel_information

-- DROP DATABASE user_travel_information;

CREATE DATABASE user_travel_information
  WITH OWNER = postgres
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'en_US.UTF-8'
       LC_CTYPE = 'en_US.UTF-8'
       CONNECTION LIMIT = -1;


       -- Table: trip

       -- DROP TABLE trip;

       CREATE TABLE trip
       (
         egn character(10),
         date_of_arrived date,
         departure_date date,
         city character(120),
         CONSTRAINT fk_egn FOREIGN KEY (egn)
             REFERENCES users (user_egn) MATCH SIMPLE
             ON UPDATE NO ACTION ON DELETE NO ACTION
       )
       WITH (
         OIDS=FALSE
       );
       ALTER TABLE trip
         OWNER TO postgres;


-- Table: users

-- DROP TABLE users;

CREATE TABLE users
(
  user_age integer,
  user_name character(80),
  user_email character(120),
  user_egn character(10),
  CONSTRAINT pk_egn UNIQUE (user_egn)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE users
  OWNER TO postgres;



--Insert into users entered values.
INSERT INTO users values(?, ?, ?, ?);

--Insert into trip entered values.
INSERT INTO trip(egn, date_of_arrived, departure_date, city) values(?, ?, ?, ?);

--Update user with values.
UPDATE users SET user_age = ?, user_name = ?, user_email = ? WHERE user_egn = ?;

 --Select all users from database.
 SELECT * FROM users;

 --Select all trips from database.
 SELECT * FROM trip;