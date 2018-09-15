CREATE TABLE IF NOT EXISTS SCHOOLS (
    ID      NUMERIC(7, 0) NOT NULL,
    NAME    VARCHAR(200)  NOT NULL,
    OWNER_EMAIL VARCHAR(250) NOT NULL,
    CREATED TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
    CONSTRAINT SCHOOL_PK PRIMARY KEY (ID)
);
CREATE SEQUENCE IF NOT EXISTS SCHOOLS_S
    START 1
    CACHE 1;
