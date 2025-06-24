CREATE TABLE TB_MEMBER_CREDENTIALS (
    user_uuid VARCHAR(36) PRIMARY KEY,
    id VARCHAR(20) NOT NULL,
    password VARCHAR(20) NOT NULL,
    create_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_date DATETIME,
    delete_date DATETIME,
    CONSTRAINT fk_tb_member_uuid
        FOREIGN KEY (user_uuid)
        REFERENCES TB_MEMBER(uuid)
);
