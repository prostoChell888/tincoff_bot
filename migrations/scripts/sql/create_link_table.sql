CREATE TABLE link
(
    link_id BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY,
    link varchar(255) NOT NULL UNIQUE ,
    chat_id BIGINT,

    CONSTRAINT PK_link_link_id PRIMARY KEY (link_id),
    CONSTRAINT FK_chat_chat_id FOREIGN KEY (chat_id) REFERENCES chat(chat_id)

);