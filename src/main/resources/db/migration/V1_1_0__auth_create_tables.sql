create table app_user
(
    username   varchar(255) primary key not null unique,
    deleted_at timestamp,
    password   varchar(255)             not null,
    profile_id int8                     not null unique
);

create table profile
(
    id        bigserial primary key not null,
    company   varchar(255),
    firstname varchar(255)          not null,
    lastname  varchar(255)          not null
);

create table user_role
(
    username varchar(255) not null,
    role     varchar(255)
);

alter table if exists app_user
    add constraint FK_app_user_profile foreign key (profile_id) references profile(id);

alter table if exists user_role
    add constraint FK_user_role_app_user foreign key (username) references app_user(username);