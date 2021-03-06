CREATE TABLE IF NOT EXISTS SCHOOL_ADMINS (
    SCHOOL_ID      NUMERIC(7, 0) NOT NULL,
    EMAIL    VARCHAR(250)  NOT NULL,
    CONSTRAINT SCHOOL_ADMINS_PK PRIMARY KEY (SCHOOL_ID, EMAIL),
    CONSTRAINT SCHOOL_ADMINS_SCHOOL_ID_FK FOREIGN KEY (SCHOOL_ID) REFERENCES SCHOOLS(ID)
);
