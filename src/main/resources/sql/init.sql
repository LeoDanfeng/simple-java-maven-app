drop table if exists sys_user;

create table sys_user
(
    id           bigint auto_increment,
    account_name varchar(20)  not null,
    nickname     varchar(20),
    password     varchar(255) not null,
    roles        varchar(50),
    primary key (id),
    constraint uni_acc unique key (account_name)
) engine=innodb default character set utf8mb4 collate utf8mb4_general_ci;


insert into sys_user
    (account_name, password, roles)
values ('xiaoming', '$2a$10$HLLQ5qZqdAkpXUM39Fh5gOM2ECUkmasHpMnkf4mLbGWWkWbAtSCVC', 'ADMIN'),
       ('xiaohong', '$2a$10$HLLQ5qZqdAkpXUM39Fh5gOM2ECUkmasHpMnkf4mLbGWWkWbAtSCVC', 'NORMAL');


drop table if exists cached_data;

create table cached_data
(
    id         bigint auto_increment,
    data       varchar(255),
    address_id bigint,
    primary key (id)
) engine=innodb default character set utf8mb4 collate utf8mb4_general_ci;

drop table if exists address;

create table address
(
    id             bigint auto_increment,
    city           varchar(20),
    province       varchar(20),
    detail_address varchar(255),
    primary key (id)
)engine=innodb default character set utf8mb4 collate utf8mb4_general_ci;


drop table if exists t_article;
create table t_article
(
    id            bigint auto_increment,
    title         varchar(255),
    author        varchar(255),
    content       varchar(255),
    like_count    int,
    collect_count int,
    comment_count int,
    created_at    timestamp,
    created_by    varchar(255),
    updated_at    timestamp,
    updated_by    varchar(255),
    primary key (id),
    key(author)
)engine=innodb default character set utf8mb4 collate utf8mb4_general_ci;

insert into t_article
    (title, author, content)
values ("How to sepnd 1/5 vocation", "xiaoming", "Stay at home on the first day, and then go to Shenzheng.");

drop table if exists t_comment;
create table t_comment
(
    id         bigint auto_increment,
    article_id bigint,
    commenter  varchar(255),
    content    varchar(255),
    like_count int,
    created_at timestamp,
    created_by varchar(255),
    updated_at timestamp,
    updated_by varchar(255),
    primary key (id),
    key(article_id)
)engine=innodb default character set utf8mb4 collate utf8mb4_general_ci;

insert into t_comment
    (article_id, commenter, content)
values (1, "xiaohong", "Nice Vovation Plan.");

drop table if exists t_reply;
create table t_reply
(
    id         bigint auto_increment,
    article_id bigint,
    comment_id bigint,
    content    varchar(255),
    reply_to   varchar(255),
    reply_from varchar(255),
    like_count int,
    created_at timestamp,
    created_by varchar(255),
    updated_at timestamp,
    updated_by varchar(255),
    primary key (id),
    key(article_id,comment_id)
)engine=innodb default character set utf8mb4 collate utf8mb4_general_ci;

insert into t_reply
    (article_id, comment_id, content, reply_to, reply_from)
values (1, 1, "Happy to hear that. What do you plan to do on the vocation?", "xiaohong", "xiaoming");





