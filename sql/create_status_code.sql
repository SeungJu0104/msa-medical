CREATE TABLE TB_STATUS_CODE (
    id VARCHAR(4) PRIMARY KEY,
    group_id INT NOT NULL,
    name VARCHAR(20) NOT NULL,
    CONSTRAINT fk_tb_status_group_id
        FOREIGN KEY (group_id)
        REFERENCES TB_STATUS_GROUP(id)
);
