create table users(
id serial,
name varchar(50),
password varchar,
email varchar(100),
primary key (id));


create table account(
id serial,
name varchar(60),
value numeric(10,2),
lineNumber numeric(11),
primary key (id));

create table users_account(
id serial,
account integer,
users integer,
primary key(id),
foreign key(users) references users(id),
foreign key(account) references account(id));

create table account_items(
id serial,
account integer,
date date,
type varchar(2),
description varchar(500),
value numeric(10,2),
primary key(id),
foreign key(account) references account(id)
);