package ru.tinkoff.scrapper.repository.JDBC;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.tinkoff.scrapper.enyity.ChatEntity;
import ru.tinkoff.scrapper.enyity.mapers.UserMapper;
import ru.tinkoff.scrapper.repository.TgChatRepository;

import java.util.List;


@Repository
@RequiredArgsConstructor
public class TgChatJDBCRepository implements TgChatRepository {

    private final UserMapper mapper = new UserMapper();

    private final JdbcTemplate template;



    @Override
    public void add(Long tgChatId) {
        String sql = "INSERT INTO chat (tg_chat_id) VALUES (?)";
         template.update(sql, tgChatId);
    }

    @Override
    public List<ChatEntity> findAll() {
        String sql = "SELECT * FROM chat";
        return template.query(sql, mapper);
    }

    @Override
    public List<ChatEntity> findByTgChatId(Long tgChatId) {
        String sql = "SELECT * FROM chat where tg_chat_id = ?";
        return template.query(sql, mapper, tgChatId);
    }

    @Override
    public List<ChatEntity> findById(Long id) {
        String sql = "SELECT * FROM chat where chat_id = ?";
        return template.query(sql, mapper, id);
    }

    @Override
    public Long removeByTgChatId(Long tgChatId) {
        final String sql = "delete from chat where tg_chat_id = ?";
        var res = template.query(sql,  new Object[]{tgChatId}, new DataClassRowMapper<>(Long.class));
        return (res.size() > 0)? res.get(0):null;
    }

    @Override
    public Long removeById(Long chatId) {
        final String sql = "delete from chat where chat_id = ? returning chat_id";
        var res =  template.query(sql, new Object[]{chatId}, new DataClassRowMapper<>(Long.class));
        return (res.size() > 0)? res.get(0):null;
    }

    @Override
    public Long updateById(Long id, Long tgChatId) {
        final String sql = "update chat set tg_chat_id = ? where chat_id = ? returning chat_id";
        var res = template.query(sql,  new Object[]{tgChatId, id}, new DataClassRowMapper<>(Long.class));
        return (res.size() > 0)? res.get(0):null;
    }

    @Override
    public void utracedLinks(Long chatId) {
        final String sql = "delete from chat_link where chat_id = ?";
        template.update(sql, chatId);

    }

    public List<Long> findChatByLinkId(Long linkId) {
        String sql = "SELECT tg_chat_id " +
                "FROM chat " +
                "JOIN chat_link cl ON chat.chat_id = cl.chat_id " +
                "WHERE link_id = ?";
        return template.queryForList(sql, Long.class, linkId);
    }

    @Override
    public List<Long> findIdByTgChatId(Long tgChatId) {
        String sql = "SELECT chat_id " +
                "FROM chat  " +
                "WHERE tg_chat_id = ?";
        return template.queryForList(sql, Long.class, tgChatId);
    }
}
