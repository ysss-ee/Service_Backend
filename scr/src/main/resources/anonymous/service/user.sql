create table user
(
    userId   int auto_increment
        primary key,
    userName varchar(10)  not null,
    password varchar(20)  not null,
    userType int          not null,
    sex      char         not null,
    email    varchar(20)  not null,
    picture  varchar(255) null
);

