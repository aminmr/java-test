insert into profile(firstname, lastname, company) values ('Mohammad Esmaeil', 'Safari', 'None');

insert into app_user (username, password, deleted_at, profile_id)
values ('ms.safari@outlook.com', '$2a$10$L1DF2QMk3QDH7ZS5OOIPeu66YFrZGakvoQcggGDlkps0z1Frrseba', null, (select id from profile limit 1));

insert into user_role(username, role) VALUES ((select username from app_user limit 1), 'ROLE_ADMIN');
