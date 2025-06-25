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