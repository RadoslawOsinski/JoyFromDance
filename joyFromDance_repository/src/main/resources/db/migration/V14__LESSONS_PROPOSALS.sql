CREATE TABLE IF NOT EXISTS LESSON_PROPOSALS (
    ID          NUMERIC(14, 0) NOT NULL,
    EMAIL   VARCHAR(250)   NOT NULL,
    LESSON_TYPE_ID   NUMERIC(7, 0)  NOT NULL,
    START_TIME  TIMESTAMP WITH TIME ZONE NOT NULL,
    END_TIME    TIMESTAMP WITH TIME ZONE NOT NULL,
    CONSTRAINT LESSON_PROPOSALS_PK PRIMARY KEY (ID),
    CONSTRAINT LESSON_PROPOSALS_TYPE_ID_FK FOREIGN KEY (LESSON_TYPE_ID) REFERENCES LESSON_TYPES (ID)
);
CREATE SEQUENCE IF NOT EXISTS LESSON_PROPOSALS_S
    START 1
    CACHE 1;

CREATE INDEX IF NOT EXISTS LESSON_PROPOSALS_TYPE_ID_IDX ON LESSON_PROPOSALS (LESSON_TYPE_ID);
CREATE INDEX IF NOT EXISTS LESSON_PROPOSALS_EMAIL_IDX ON LESSON_PROPOSALS (EMAIL);
