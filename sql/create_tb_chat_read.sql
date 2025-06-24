CREATE TABLE `TB_CHAT_READ` (
  `room_id` int(11) NOT NULL,
  `uuid` varchar(36) NOT NULL,
  `message_id` int(11) NOT NULL,
  `read_time` datetime DEFAULT current_timestamp(),
  PRIMARY KEY (`room_id`,`uuid`,`message_id`),
  KEY `TB_CHAT_READE_TB_CHAT_JOIN_FK_1` (`uuid`,`room_id`),
  KEY `TB_CHAT_READE_TB_CHAT_MESSAGE_FK` (`message_id`),
  CONSTRAINT `TB_CHAT_READE_TB_CHAT_JOIN_FK` FOREIGN KEY (`uuid`, `room_id`) REFERENCES `TB_CHAT_JOIN` (`uuid`, `room_id`),
  CONSTRAINT `TB_CHAT_READE_TB_CHAT_JOIN_FK_1` FOREIGN KEY (`uuid`, `room_id`) REFERENCES `TB_CHAT_JOIN` (`uuid`, `room_id`),
  CONSTRAINT `TB_CHAT_READE_TB_CHAT_MESSAGE_FK` FOREIGN KEY (`message_id`) REFERENCES `TB_CHAT_MESSAGE` (`message_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
