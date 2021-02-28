insert into springsecurity.users (age, first_name, last_name, password, username)
values (25, 'Oleg', 'Petrov' , '$2y$12$5eUZLZ6/Vj040vwPurpQ0OIIEqGcBn9M.491M3DBGOjQAvDsxgEy2', '111@mail.ru');
insert into springsecurity.users (age, first_name, last_name, password, username)
values (35, 'Elena', 'Sidorova' , '$2y$12$5eUZLZ6/Vj040vwPurpQ0OIIEqGcBn9M.491M3DBGOjQAvDsxgEy2', 'admin@mail.ru');
insert into springsecurity.roles (name) value ('ROLE_USER');
insert into springsecurity.roles (name) value ('ROLE_ADMIN');
insert into springsecurity.users_roles (user_id, role_id) values (1,2);
insert into springsecurity.users_roles (user_id, role_id) values (2,1);