CREATE TABLE chat_link
(
    chat_id BIGINT NOT NULL,
    link_id BIGINT NOT NULL,
    PRIMARY KEY (chat_id, link_id),
    FOREIGN KEY (chat_id) REFERENCES chat (chat_id),
    FOREIGN KEY (link_id) REFERENCES link (link_id)
);