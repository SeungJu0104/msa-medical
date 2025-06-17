CREATE TABLE IF NOT EXISTS TB_MEMBER(

                                        MEMBER_UUID VARCHAR(36),
    MEMBER_ROLE VARCHAR(4),
    MEMBER_NAME VARCHAR(20) ,
    MEMBER_RRN VARCHAR(15),
    MEMBER_PHONE VARCHAR(15),
    MEMBER_CREATE_DATE DATETIME DEFAULT CURRENT_TIMESTAMP,
    MEMBER_UPDATE_DATE DATETIME,
    MEMBER_DELETE_DATE DATETIME,
    CONSTRAINT TB_MEMBER_PK
    PRIMARY KEY (MEMBER_UUID),
    CONSTRAINT MEMBER_ROLE_CHK
    CHECK ( MEMBER_ROLE LIKE 'R%' )

    );

insert into TB_MEMBER(
    MEMBER_UUID,
    MEMBER_ROLE,
    MEMBER_NAME
)VALUES(
           "001",
           "R002",
           "김수영"
       );

insert into TB_MEMBER(
    MEMBER_UUID,
    MEMBER_ROLE,
    MEMBER_NAME
)VALUES(
           "002",
           "R002",
           "김영선"
       );

insert into TB_MEMBER(
    MEMBER_UUID,
    MEMBER_ROLE,
    MEMBER_NAME
)VALUES(
           "003",
           "R002",
           "김찬희"
       );