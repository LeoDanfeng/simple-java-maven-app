drop table if exists sys_user;

create table sys_user(
    id bigint increment ,
    accountName varchar(20) not null,
    nickname varchar(20),
    password varchar(30) not null,
    roles varchar(50),
    primary key(id),
    constraint uni_acc unique key(accountName)
) engine=innodb default character set utf8mb4 collate utf8mb4_general_ci;


insert into sys_user
    (accountName,password) values
('xiaoming','xm123456'),
('xiaohong','xh123456');

