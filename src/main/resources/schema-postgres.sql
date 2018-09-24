CREATE USER solo WITH
  LOGIN
  NOSUPERUSER
  INHERIT
  CREATEDB
  CREATEROLE
  NOREPLICATION;

-- GRANT solo TO admin;


CREATE DATABASE skarbnik OWNER solo;

\c skarbnik

ALTER USER solo PASSWORD 'password';

CREATE TABLE expenses (
  id SERIAL,
  qty decimal NOT NULL,
  description varchar(256),
  duedate timestamp not null,
  PRIMARY KEY (id)
);

ALTER TABLE expenses OWNER TO solo;

--CREATE TABLE payedexpensesloadbilling (
--  id INTEGER NOT NULL,
--  user varchar(50) REFERENCES user(username),
--  expenses_id INTEGER REFERENCES expenses(id),
--  PRIMARY KEY (id)
--);


create table users (
    username varchar(50) not null primary key,
    password varchar(120) not null,
    childName varchar(120) not null,
    childSurname varchar(120) not null,
    enabled boolean not null
);

ALTER TABLE users OWNER TO solo;

create table authorities (
    username varchar(50) not null,
    authority varchar(50) not null,
    foreign key (username) references users (username)
);

ALTER TABLE authorities OWNER TO solo;

CREATE TABLE incomes (
  id SERIAL,
  username varchar(50) REFERENCES users(username),
  account varchar(50) NOT NULL,
  qty decimal NOT NULL,
  description varchar(256),
  transactiontime timestamp not null,
  PRIMARY KEY (id)
);

ALTER TABLE incomes OWNER TO solo;

