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

insert into users(username, password, childName, childSurname, enabled) values('marta','marta','','',true);
insert into authorities(username,authority) values('marta','ROLE_ADMIN');



insert into users(childSurname, childName, username, password,enabled) values('Augustyn' , 'Emilia' , 'augustyn.emilia' , 'augustyn.emilia',true);
insert into users(childSurname, childName, username, password,enabled) values('Chwierut' , 'Gaja' , 'chwierut.gaja' , 'chwierut.gaja',true);
insert into users(childSurname, childName, username, password,enabled) values('Dobrowolska' , 'Emilia' , 'dobrowolska.emilia' , 'dobrowolska.emilia',true);
insert into users(childSurname, childName, username, password,enabled) values('Jarek' , 'Patryk' , 'jarek.patryk' , 'jarek.patryk',true);
insert into users(childSurname, childName, username, password,enabled) values('Kędra' , 'Aleksander' , 'kedra.aleksander' , 'kedra.aleksander',true);
insert into users(childSurname, childName, username, password,enabled) values('Kowalski' , 'Mateusz' , 'kowalski.mateusz' , 'kowalski.mateusz',true);
insert into users(childSurname, childName, username, password,enabled) values('Kucharczyk' , 'Maks' , 'kucharczyk.maks' , 'kucharczyk.maks',true);
insert into users(childSurname, childName, username, password,enabled) values('Litwicka' , 'Alicja' , 'litwicka.alicja' , 'litwicka.alicja',true);
insert into users(childSurname, childName, username, password,enabled) values('Mazurek' , 'Adam' , 'mazurek.adam' , 'mazurek.adam',true);
insert into users(childSurname, childName, username, password,enabled) values('Miecznik' , 'Grzegorz' , 'miecznik.grzegorz' , 'miecznik.grzegorz',true);
insert into users(childSurname, childName, username, password,enabled) values('Olszewska' , 'Magdalena' , 'olszewska.magdalena' , 'olszewska.magdalena',true);
insert into users(childSurname, childName, username, password,enabled) values('Ozimkiewicz' , 'Hanna' , 'ozimkiewicz.hanna' , 'ozimkiewicz.hanna',true);
insert into users(childSurname, childName, username, password,enabled) values('Pala' , 'Zofia' , 'pala.zofia' , 'pala.zofia',true);
insert into users(childSurname, childName, username, password,enabled) values('Piskorz' , 'Maja' , 'piskorz.maja' , 'piskorz.maja',true);
insert into users(childSurname, childName, username, password,enabled) values('Sądel' , 'Urszula' , 'sadel.urszula' , 'sadel.urszula',true);
insert into users(childSurname, childName, username, password,enabled) values('Sitarz' , 'Eryk' , 'sitarz.eryk' , 'sitarz.eryk',true);
insert into users(childSurname, childName, username, password,enabled) values('Soluch' , 'Magda' , 'soluch.magda' , 'soluch.magda',true);
insert into users(childSurname, childName, username, password,enabled) values('Szczerbińska' , 'Alicja' , 'szczerbinska.alicja' , 'szczerbinska.alicja',true);
insert into users(childSurname, childName, username, password,enabled) values('Szostek' , 'Krzysztof' , 'szostek.krzysztof' , 'szostek.krzysztof',true);
insert into users(childSurname, childName, username, password,enabled) values('Śrutowski' , 'Franciszek' , 'srutowski.franciszek' , 'srutowski.franciszek',true);
insert into users(childSurname, childName, username, password,enabled) values('Tekielski' , 'Igor' , 'tekielski.igor' , 'tekielski.igor',true);
insert into users(childSurname, childName, username, password,enabled) values('Wadoń' , 'Błażej' , 'wadon.blazej' , 'wadon.blazej',true);
insert into users(childSurname, childName, username, password,enabled) values('Wcisło' , 'Filip' , 'wcislo.filip' , 'wcislo.filip',true);
insert into users(childSurname, childName, username, password,enabled) values('Żelazny' , 'Krzysztof' , 'zelazny.krzysztof' , 'zelazny.krzysztof',true);
insert into users(childSurname, childName, username, password,enabled) values('Żyrkowska' , 'Milena' , 'zyrkowska.milena' , 'zyrkowska.milena' , true);

insert into authorities(username,authority) values('augustyn.emilia','ROLE_USER');
insert into authorities(username,authority) values('chwierut.gaja','ROLE_USER');
insert into authorities(username,authority) values('dobrowolska.emilia','ROLE_USER');
insert into authorities(username,authority) values('jarek.patryk','ROLE_USER');
insert into authorities(username,authority) values('kedra.aleksander','ROLE_USER');
insert into authorities(username,authority) values('kowalski.mateusz','ROLE_USER');
insert into authorities(username,authority) values('kucharczyk.maks','ROLE_USER');
insert into authorities(username,authority) values('litwicka.alicja','ROLE_USER');
insert into authorities(username,authority) values('mazurek.adam','ROLE_USER');
insert into authorities(username,authority) values('miecznik.grzegorz','ROLE_USER');
insert into authorities(username,authority) values('olszewska.magdalena','ROLE_USER');
insert into authorities(username,authority) values('ozimkiewicz.hanna','ROLE_USER');
insert into authorities(username,authority) values('pala.zofia','ROLE_USER');
insert into authorities(username,authority) values('piskorz.maja','ROLE_USER');
insert into authorities(username,authority) values('sadel.urszula','ROLE_USER');
insert into authorities(username,authority) values('sitarz.eryk','ROLE_USER');
insert into authorities(username,authority) values('soluch.magda','ROLE_USER');
insert into authorities(username,authority) values('szczerbinska.alicja','ROLE_USER');
insert into authorities(username,authority) values('szostek.krzysztof','ROLE_USER');
insert into authorities(username,authority) values('srutowski.franciszek','ROLE_USER');
insert into authorities(username,authority) values('tekielski.igor','ROLE_USER');
insert into authorities(username,authority) values('wadon.blazej','ROLE_USER');
insert into authorities(username,authority) values('wcislo.filip','ROLE_USER');
insert into authorities(username,authority) values('zelazny.krzysztof','ROLE_USER');
insert into authorities(username,authority) values('zyrkowska.milena','ROLE_USER');