create table user
(
    userId    int auto_increment
        primary key,
    username  varchar(10)  not null,
    password  varchar(20)  not null,
    user_type int          not null,
    sex       char         null,
    email     varchar(20)  not null,
    picture   varchar(255) null,
    college   varchar(20)  null,
    major     varchar(20)  null,
    grade     varchar(20)  null,
    phone     varchar(20)  null
)
    engine = InnoDB;


