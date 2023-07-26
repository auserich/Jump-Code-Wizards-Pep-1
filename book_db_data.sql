-- book

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
    
-- user

insert into user(first_name, last_name, username, password)
	values('Amelia', 'Anderson', 'a.anderson@email.com', 'password123');
insert into user(first_name, last_name, username, password)
	values('Benjamin', 'Brown', 'b.brown@email.com', 'password123');
insert into user(first_name, last_name, username, password)
	values('Charlotte', 'Carter', 'c.carter@email.com', 'password123');
insert into user(first_name, last_name, username, password)
	values('Daniel', 'Davis', 'd.davis@email.com', 'password123');
insert into user(first_name, last_name, username, password)
	values('Emma', 'Evans', 'e.evans@gmail.com', 'password123');
    
-- book_user

insert into book_user (book_id, user_id, progress)
	values
    (
		(select book_id from book),
		(select user_id from user),
        'In-Progress'
	);
insert into book_user (book_id, user_id)
	values
    (
		(select book_id from book where book.name = 'Uncanny Enigmas'),
		(select user_id from user where user.first_name = 'Amelia')
	);