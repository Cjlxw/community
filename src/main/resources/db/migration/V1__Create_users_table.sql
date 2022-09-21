create table USERS
(
    ID           INTEGER auto_increment,
    ACCOUNT_ID   CHARACTER,
    NAME         CHARACTER,
    TOKEN        CHARACTER(1024),
    GMT_CREATE   BIGINT,
    GMT_MODIFIED BIGINT
);
