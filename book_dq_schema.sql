drop database if exists book_db;
create database book_db;
use book_db;

-- books

CREATE TABLE book
(
    book_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL
);

-- user

CREATE TABLE user
(
	user_id INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    user VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);
    
-- book_user

CREATE table book_user 
(
	book_id INT,
    user_id INT,
    progress ENUM ('Not Completed', 'In-Progress', 'Completed') NOT NULL,
    rating SMALLINT CHECK (rating BETWEEN 1 AND 5),
    CONSTRAINT book_user_pk PRIMARY KEY (book_id, user_id),
    CONSTRAINT fk_book FOREIGN KEY (book_id) REFERENCES book (book_id),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES user (user_id)
);