create table report
(
    reportId int auto_increment
        primary key,
    postId   int           null,
    content  varchar(500)  null,
    reason   varchar(500)  null,
    approval int default 0 not null
);

