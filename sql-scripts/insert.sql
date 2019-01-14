--
-- Default passwords here are: fun123
--

INSERT INTO `users` 
VALUES 
(1,'admin','$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K','Janusz','Tracz','666666666'),
(2,'moderator','$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K','Grażyna','Opijo','123456789'),
(3,'user','$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K','Piotr','Niemamnazwiska','987654321');

--
-- Dumping data for table `authorities`
--

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