DROP TABLE IF EXISTS `person`;
CREATE TABLE `person` (
  `id` int primary key auto_increment,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `birth_day` date DEFAULT NULL,
  `phone_number` varchar(32) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);