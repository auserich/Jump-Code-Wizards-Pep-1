drop database if exists book_db;
create database book_db;
use book_db;

-- books

CREATE TABLE book
(
    book_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL
);

insert into book(name) 
	values('Techno Odyssey');
insert into book(name) 
	values('Silent Riddles');
insert into book(name) 
	values('Uncanny Enigmas');
insert into book(name) 
	values('Solar Drifters');
insert into book(name)
	values('Veiled Intrigue');
insert into book(name)
	values('Quantum Cronicles');
insert into book(name)
	values('Nebulous Encounters');
insert into book(name)
	values('Murmurs of Deceit');
insert into book(name)
	values('Labyrinth of Clues');
insert into book(name)
	values('Beyond the Event Horizon');

-- users

CREATE TABLE user
(
	user_id INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    user_name VARCHAR(255) NOT NULL,
    user_pass VARCHAR(255) NOT NULL
);

insert into user(first_name, last_name, user_name, user_pass)
	values('Amelia', 'Anderson', 'a.anderson@email.com', 'password123');
insert into user(first_name, last_name, user_name, user_pass)
	values('Benjamin', 'Brown', 'b.brown@email.com', 'password123');
insert into user(first_name, last_name, user_name, user_pass)
	values('Charlotte', 'Carter', 'c.carter@email.com', 'password123');
insert into user(first_name, last_name, user_name, user_pass)
	values('Daniel', 'Davis', 'd.davis@email.com', 'password123');
insert into user(first_name, last_name, user_name, user_pass)
	values('Emma', 'Evans', 'e.evans@gmail.com', 'password123');