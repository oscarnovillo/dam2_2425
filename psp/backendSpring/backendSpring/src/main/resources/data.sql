delete from alumnos;
insert into alumnos (name) values ('Juanito');
insert into alumnos (name) values ('Jorgito');
insert into alumnos (name) values ('Jaimito');

delete from user_roles;
delete from roles;
delete from users;



insert into roles (id,rol) values (1,'ADMIN');
insert into roles (id,rol) values (2,'USER');

insert into users (id,name,password) values (1,'admin','$2a$10$aguIiT4W2Ki1eX9CijS8B.GDbc8eMP0dh0NbGdkyrJmz67wBl2tY2');
insert into users (id,name,password) values (2,'pedro','$2a$10$aguIiT4W2Ki1eX9CijS8B.GDbc8eMP0dh0NbGdkyrJmz67wBl2tY2');
insert into users (id,name,password) values (3,'juan','$2a$10$aguIiT4W2Ki1eX9CijS8B.GDbc8eMP0dh0NbGdkyrJmz67wBl2tY2');
insert into users (id,name,password) values (4,'jose','$2a$10$aguIiT4W2Ki1eX9CijS8B.GDbc8eMP0dh0NbGdkyrJmz67wBl2tY2');
insert into users (id,name,password) values (5,'luis','$2a$10$aguIiT4W2Ki1eX9CijS8B.GDbc8eMP0dh0NbGdkyrJmz67wBl2tY2');
insert into users (id,name,password) values (6,'maria','$2a$10$aguIiT4W2Ki1eX9CijS8B.GDbc8eMP0dh0NbGdkyrJmz67wBl2tY2');
insert into users (id,name,password) values (7,'ana','$2a$10$aguIiT4W2Ki1eX9CijS8B.GDbc8eMP0dh0NbGdkyrJmz67wBl2tY2');
insert into users (id,name,password) values (8,'laura','$2a$10$aguIiT4W2Ki1eX9CijS8B.GDbc8eMP0dh0NbGdkyrJmz67wBl2tY2');
insert into users (id,name,password) values (9,'pilar','$2a$10$aguIiT4W2Ki1eX9CijS8B.GDbc8eMP0dh0NbGdkyrJmz67wBl2tY2');
insert into users (id,name,password) values (10,'carmen','$2a$10$aguIiT4W2Ki1eX9CijS8B.GDbc8eMP0dh0NbGdkyrJmz67wBl2tY2');

insert into user_roles (user_id,roles_id) values (1,1);
insert into user_roles (user_id,roles_id) values (2,2);
insert into user_roles (user_id,roles_id) values (3,2);
insert into user_roles (user_id,roles_id) values (4,2);
insert into user_roles (user_id,roles_id) values (5,2);
insert into user_roles (user_id,roles_id) values (6,2);
insert into user_roles (user_id,roles_id) values (7,2);
insert into user_roles (user_id,roles_id) values (8,2);
insert into user_roles (user_id,roles_id) values (9,2);
insert into user_roles (user_id,roles_id) values (10,2);
