CREATE TABLE link
(
    link_id     BIGINT       NOT NULL GENERATED ALWAYS AS IDENTITY,
    link        varchar(255) NOT NULL UNIQUE,
    last_update TIMESTAMP    NOT NULL,

    CONSTRAINT PK_link_link_id PRIMARY KEY (link_id)
);