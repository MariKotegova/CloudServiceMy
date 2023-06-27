create schema if not exists myClSt;

create table if not exists myClSt.files
(
    id int primary key auto_increment,
    filename varchar(255) not null,
    size int not null,
    deleted int,
    path varchar(255) not null
    );


create table if not exists myClSt.user(
    id int primary key auto_increment,
    login varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    role varchar(255) NOT NULL,
    );

insert into myClSt.user (login, password, role)
values ('admin', 'admin', 'ADMIN');