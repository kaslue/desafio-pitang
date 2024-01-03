CREATE TABLE `cars` (
  `id` bigint auto_increment NOT NULL,
  `color` varchar(20) DEFAULT NULL,
  `country` varchar(30) DEFAULT NULL,
  `license_plate` varchar(20) DEFAULT NULL,
  `model` varchar(50) DEFAULT NULL,
  `manufactured_year` int DEFAULT NULL
);

INSERT INTO `cars` (`id`, `color`, `country`, `license_plate`, `model`, `manufactured_year`) VALUES
(1, 'Prata', 'Brasil', 'HTY-2550', 'Apollo 1.8', 1992),
(2, 'Branco', 'Brasil', 'PEP-8J97', 'HB20S 1.6 16v', 2021),
(15, 'Branco', 'Brasil', 'XHY-7777', 'Fiat Uno', 2014),
(16, 'Cinza Escuro', 'Brasil', 'HYZ-2D37', 'BYD seal', 2023),
(17, 'Verde', 'Brasil', 'HAJ-3782', 'Onix 1.0 16v', 2017),
(19, 'Prata', 'Brasil', 'FHS-3H38', 'HB20S 1.0 Turbo', 2022),
(25, 'Preto', 'Brasil', 'DJS-2J38', 'Duster 1.6', 2022),
(26, 'Preto', 'Brasil', 'DJH-3721', 'Palio 2014', 2014),
(27, 'Branco', 'Brasil', 'DHS-3872', 'Logan', 2017);

CREATE TABLE `roles` (
  `id` int auto_increment NOT NULL,
  `name` varchar(20) DEFAULT NULL
);

INSERT INTO `roles` (`id`, `name`) VALUES
(1, 'ROLE_USER'),
(2, 'ROLE_MODERATOR'),
(3, 'ROLE_ADMIN');

CREATE TABLE `users` (
  `id` bigint auto_increment NOT NULL,
  `email` varchar(50) DEFAULT NULL,
  `password` varchar(120) DEFAULT NULL,
  `login` varchar(20) DEFAULT NULL,
  `birthday` datetime DEFAULT NULL,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `phone` varchar(30) DEFAULT NULL,
  `dh_creation` datetime DEFAULT NULL,
  `dh_last_login` datetime DEFAULT NULL,
  `dh_last_update` datetime DEFAULT NULL
);

INSERT INTO `users` (`id`, `email`, `password`, `login`, `birthday`, `first_name`, `last_name`, `phone`, `dh_creation`, `dh_last_login`, `dh_last_update`) VALUES
(2, 'admin@admin.com', '$2a$10$hcMskL/TtEo1VGv.5PQXeOBFqoHUNj/uW/Z40O/jn.RY8XzddblpW', 'admin', '1999-01-01 00:00:00', 'Administrador', 'Do Sistema', '81 99288-2384', CURRENT_TIMESTAMP , CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'moderator@moderator.com', '$2a$10$hcMskL/TtEo1VGv.5PQXeOBFqoHUNj/uW/Z40O/jn.RY8XzddblpW', 'moderator', NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 'admin2@admin.com', '$2a$10$hcMskL/TtEo1VGv.5PQXeOBFqoHUNj/uW/Z40O/jn.RY8XzddblpW', 'admin2', '1988-03-01 00:00:00', 'Carlos', 'Filho', '(81) 98888-2725',CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7, 'pedro.teste@teste.com', '$2a$10$hcMskL/TtEo1VGv.5PQXeOBFqoHUNj/uW/Z40O/jn.RY8XzddblpW', 'pedro', '1988-03-28 00:00:00', 'Pedro', 'Machado', '81 92834-2834', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


CREATE TABLE `user_cars` (
  `user_id` bigint NOT NULL,
  `car_id` bigint NOT NULL
);

INSERT INTO `user_cars` (`user_id`, `car_id`) VALUES
(2, 25),
(3, 15),
(3, 16),
(4, 1),
(4, 2),
(4, 17),
(4, 19),
(7, 26),
(7, 27);

CREATE TABLE `user_roles` (
  `user_id` bigint NOT NULL,
  `role_id` int NOT NULL
);

INSERT INTO `user_roles` (`user_id`, `role_id`) VALUES
(2, 3),
(3, 2),
(4, 1),
(7, 1);

ALTER TABLE `cars`
  ADD PRIMARY KEY (`id`);

CREATE UNIQUE INDEX UK_CAR_1 ON cars (license_plate,country);


ALTER TABLE `roles`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

CREATE UNIQUE INDEX UK_USER_1 ON users (email);

CREATE UNIQUE INDEX UK_USER_2 ON users (login);

ALTER TABLE `user_cars`
  ADD PRIMARY KEY (`user_id`,`car_id`);

ALTER TABLE `user_roles`
  ADD PRIMARY KEY (`user_id`,`role_id`);

ALTER TABLE user_cars
ADD FOREIGN KEY (car_id)
REFERENCES cars (id);

ALTER TABLE user_cars
ADD FOREIGN KEY (user_id)
REFERENCES users (id);

ALTER TABLE user_roles
ADD FOREIGN KEY (user_id)
REFERENCES users (id);

ALTER TABLE user_roles
ADD FOREIGN KEY (role_id)
REFERENCES roles (id);

COMMIT;