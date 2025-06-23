CREATE TABLE TB_MEMBER (
    uuid VARCHAR(36) PRIMARY KEY,
    member_role VARCHAR(4) NOT NULL,
    name VARCHAR(20) NOT NULL,
    rrn VARCHAR(15) NOT NULL UNIQUE,
    phone VARCHAR(15) NOT NULL UNIQUE,
    create_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_date DATETIME,
    delete_date DATETIME,
    CONSTRAINT fk_tb_status_code_id
        FOREIGN KEY (member_role)
        REFERENCES TB_STATUS_CODE(id),
    CONSTRAINT chk_member_role_prefix
        CHECK (member_role LIKE 'R%')
);


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


CREATE TABLE `TB_CHAT_ROOM` (
  `room_id` int(11) NOT NULL AUTO_INCREMENT,
  `room_name` varchar(100) NOT NULL,
  `create_date` datetime DEFAULT current_timestamp(),
  PRIMARY KEY (`room_id`)
) ENGINE=InnoDB AUTO_INCREMENT=74 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;