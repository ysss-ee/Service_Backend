create table post
(
    postId     int auto_increment
        primary key,
    userId     int                                 not null,
    title      varchar(50)                         null,
    content    varchar(500)                        not null,
    level      int                                 not null,
    hide       int                                 not null,
    response   varchar(500)                        null,
    comment    varchar(500)                        null,
    createTime timestamp default CURRENT_TIMESTAMP not null,
    state      int       default 1                 null
);

