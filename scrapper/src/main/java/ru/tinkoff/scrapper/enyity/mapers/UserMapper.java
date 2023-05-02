package ru.tinkoff.scrapper.enyity.mapers;

import org.springframework.jdbc.core.RowMapper;
import ru.tinkoff.scrapper.enyity.ChatEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<ChatEntity> {

    @Override
    public ChatEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        ChatEntity user = new ChatEntity();
        user.setId(rs.getLong("chat_id"));
        user.setTgChatId(rs.getLong("tg_chat_id"));

        return user;
    }



}
