CREATE TABLE `TB_PAYMENT` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `treatment_id` varchar(36) NOT NULL,
  `payment_status` varchar(4) NOT NULL,
  `payment` int(11) NOT NULL,
  `payment_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `TB_PAYMENT_TB_STATUS_CODE_FK_1` (`payment_status`),
  CONSTRAINT `TB_PAYMENT_TB_STATUS_CODE_FK` FOREIGN KEY (`payment_status`) REFERENCES `TB_STATUS_CODE` (`id`),
  CONSTRAINT `TB_PAYMENT_TB_STATUS_CODE_FK_1` FOREIGN KEY (`payment_status`) REFERENCES `TB_STATUS_CODE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;