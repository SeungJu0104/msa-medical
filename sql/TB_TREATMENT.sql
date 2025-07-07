CREATE TABLE `TB_TREATMENT` (
  `id` int(11) AUTO_INCREMENT,
  `treat_content` text DEFAULT NULL,
  `treat_date` date NOT NULL DEFAULT current_timestamp(),
  `uuid` varchar(36) NOT NULL,
  `write_yn` char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id`),
  KEY `TB_TREATMENT_TB_RECEPTION_FK` (`uuid`),
  CONSTRAINT `TB_TREATMENT_TB_RECEPTION_FK` FOREIGN KEY (`uuid`) REFERENCES `TB_RECEPTION` (`UUID`)
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;