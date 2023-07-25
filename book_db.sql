drop database if exists book_db;
create database book_db;
use book_db;

CREATE TABLE book (
    book_id INT PRIMARY KEY 
				AUTO_INCREMENT,
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