CREATE TABLE `TB_CHAT_JOIN` (
  `room_id` int(11) NOT NULL,
  `uuid` varchar(36) NOT NULL,
  `join_time` datetime DEFAULT NULL,
  `out_time` datetime DEFAULT NULL,
  PRIMARY KEY (`uuid`,`room_id`),
  KEY `TB_CHAT_JOIN_TB_CHAT_ROOM_FK` (`room_id`),
  CONSTRAINT `TB_CHAT_JOIN_TB_CHAT_ROOM_FK` FOREIGN KEY (`room_id`) REFERENCES `TB_CHAT_ROOM` (`room_id`),
  CONSTRAINT `TB_CHAT_JOIN_TB_MEMBER_FK` FOREIGN KEY (`uuid`) REFERENCES `TB_MEMBER` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
