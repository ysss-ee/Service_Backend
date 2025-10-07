create table acceptance
(
    id          int auto_increment
        primary key,
    user_id     int null,
    post_id     int null,
    create_time timestamp default CURRENT_TIMESTAMP null,
    state       int       default 1 not null comment '解决中，已解决，已取消123',
    delete_time datetime null
);

create table message
(
    id             int auto_increment
        primary key,
    accept_user_id int not null,
    content        varchar(500) null,
    create_time    timestamp default CURRENT_TIMESTAMP null
);

create table post
(
    post_id        int auto_increment
        primary key,
    user_id        int                                 not null,
    username       varchar(20) null,
    title          varchar(50) null,
    content        varchar(500)                        not null,
    level          int       default 1                 not null comment '紧急情况',
    hide           int       default 1                 not null comment '是否匿名12',
    response       varchar(500) null,
    comment        varchar(500) null comment '本人评价',
    create_time    timestamp default CURRENT_TIMESTAMP not null,
    state          int       default 1                 not null comment '待接单，已接单，已解决, 已删除1234',
    accept_user_id int       default 0 null comment '接单者',
    image          varchar(255) null
);

create table report
(
    report_id int auto_increment
        primary key,
    post_id   int null,
    content   varchar(500) null,
    reason    varchar(500) null,
    approval  int default 0 not null
);

create table response
(
    user_id     int auto_increment
        primary key,
    post_id     int null,
    content     varchar(500) null,
    create_time timestamp default CURRENT_TIMESTAMP null
);

create table user
(
    user_id   int auto_increment
        primary key,
    username  varchar(10) not null,
    password  varchar(20) not null,
    user_type int         not null,
    sex       char null,
    email     varchar(20) not null,
    picture   varchar(255) null,
    college   varchar(50) null,
    major     varchar(50) null,
    grade     varchar(10) null,
    phone     varchar(20) null
);


