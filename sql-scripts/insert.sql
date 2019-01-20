--
-- Domyślen hasło to : test123
--

INSERT INTO `users` 
VALUES 
(1,'admin','$2a$10$GwIyYsDbl78/ytF5j7O1reEZCOQ8J1of/NNpZjuy.K52a6n.XdfN6','Janusz','Tracz','666666666'),
(2,'moderator','$2a$10$GwIyYsDbl78/ytF5j7O1reEZCOQ8J1of/NNpZjuy.K52a6n.XdfN6','Grażyna','Opijo','123456789'),
(3,'usercustomer','$2a$10$GwIyYsDbl78/ytF5j7O1reEZCOQ8J1of/NNpZjuy.K52a6n.XdfN6','Piotr','Niemamnazwiska','987654321');

INSERT INTO `roles` 
VALUES 
(1,'ROLE_CUSTOMER'),
(2,'ROLE_MODERATOR'),
(3,'ROLE_ADMIN');



INSERT INTO `users_roles` 
VALUES 
(1,1),
(1,2),
(1,3),
(2,1),
(2,2),
(3,3);