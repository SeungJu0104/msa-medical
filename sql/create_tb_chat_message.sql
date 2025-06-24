
CREATE TABLE `TB_CHAT_MESSAGE` (
  `message_id` int(11) NOT NULL AUTO_INCREMENT,
  `room_id` int(11) NOT NULL,
  `uuid` varchar(36) NOT NULL,
  `content` text DEFAULT NULL,
  `create_date` datetime DEFAULT current_timestamp(),
  PRIMARY KEY (`message_id`),
  KEY `TB_CHAT_MESSAGE_TB_CHAT_JOIN_FK_1` (`uuid`,`room_id`),
  CONSTRAINT `TB_CHAT_MESSAGE_TB_CHAT_JOIN_FK` FOREIGN KEY (`uuid`, `room_id`) REFERENCES `TB_CHAT_JOIN` (`uuid`, `room_id`),
  CONSTRAINT `TB_CHAT_MESSAGE_TB_CHAT_JOIN_FK_1` FOREIGN KEY (`uuid`, `room_id`) REFERENCES `TB_CHAT_JOIN` (`uuid`, `room_id`)
) ENGINE=InnoDB AUTO_INCREMENT=440 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

