BEGIN TRANSACTION;

DROP TABLE IF EXISTS word_list;

CREATE TABLE word_entry (
    id SERIAL,
    word varchar(50) NOT NULL,
    usage varchar(10) NOT NULL,
    CONSTRAINT PK_word_entry PRIMARY KEY (id),
    CONSTRAINT UQ_word UNIQUE (word),
    CONSTRAINT CK_usage_options CHECK (usage IN ('common', 'not-common'))
);

COMMIT;