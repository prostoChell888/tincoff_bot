package ru.tinkoff.scrapper.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.tinkoff.scrapper.enyity.ChatEntity;
import ru.tinkoff.scrapper.enyity.mapers.UserMapper;

import java.util.List;


@RequiredArgsConstructor
@Repository
public class TgChatRepository {

    private final UserMapper mapper = new UserMapper();
    @Autowired
    private final JdbcTemplate template;

    public Integer add(Long chatId) {
        String sql = "INSERT INTO chat (tg_chat_id) VALUES (?)";
        return template.update(sql, chatId);
    }

    public List<ChatEntity> findAll() {
        String sql = "SELECT * FROM chat";
        return template.query(sql, mapper);
    }

    public List<ChatEntity> findById(Long tgChatId) {
        String sql = "SELECT * FROM chat where tg_chat_id = ?";
        return template.query(sql, mapper, tgChatId);
    }

    public Integer removeByChatId(Long chatId) {
        final  String sql = "delete from chat where chat_id = ?";
        return template.update(sql, chatId);
    }

    public Integer removeById(Long tgChatId) {
        final  String sql = "delete from chat where tg_chat_id = ?";
        return template.update(sql, tgChatId);
    }


    public Integer updateById(Long id, Long tgChatId) {
        final  String sql = "update chat set chat_id = ? where tg_chat_id = ?";
        return template.update(sql, id, tgChatId);
    }

    public void utracedLinks(Long chatId) {
        final  String sql = "delete from chat_link where chat_id = ?";
         template.update(sql, chatId);

    }
}
