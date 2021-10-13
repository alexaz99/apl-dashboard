CREATE TABLE Customer (
    id IDENTITY,
    name VARCHAR(200),
    dob DATE,
    gender VARCHAR(200)
);

CREATE TABLE Address (
    id              INTEGER,
    customer_key    VARCHAR(200),
    street          VARCHAR(200),
    city            VARCHAR(200)
);

create TABLE Book (
    id IDENTITY,
    title VARCHAR(200)
);

CREATE TABLE Book_Author (
    book INTEGER,
    author INTEGER
);

CREATE TABLE Author (
    id IDENTITY,
    name VARCHAR(200)
);





CREATE TABLE IF NOT EXISTS category (id INTEGER IDENTITY PRIMARY KEY, name VARCHAR(100), description VARCHAR(2000), age_group VARCHAR(20), created DATETIME, inserted BIGINT);
CREATE TABLE IF NOT EXISTS Lego_Set (id INTEGER, name VARCHAR(100), min_Age INTEGER, max_Age INTEGER);
CREATE TABLE IF NOT EXISTS Handbuch (handbuch_id INTEGER, author VARCHAR(100), text CLOB);
CREATE TABLE IF NOT EXISTS Model (name VARCHAR(100), description CLOB, lego_set INTEGER);