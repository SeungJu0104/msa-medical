CREATE TABLE `TB_ATTACHMENT` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `treatment_id` int(11) NOT NULL,
  `original_name` varchar(100) NOT NULL,
  `file_name` varchar(100) NOT NULL,
  `path` varchar(300) NOT NULL,
  `size` int(11) NOT NULL,
  `content_type` varchar(100) NOT NULL,
  `extension` varchar(10) NOT NULL,
  `create_date` datetime NOT NULL DEFAULT current_timestamp(),
  `update_date` datetime DEFAULT NULL,
  `delete_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `TB_ATTACHMENT_TB_TREATMENT_FK` (`treatment_id`),
  CONSTRAINT `TB_ATTACHMENT_TB_TREATMENT_FK` FOREIGN KEY (`treatment_id`) REFERENCES `TB_TREATMENT` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;