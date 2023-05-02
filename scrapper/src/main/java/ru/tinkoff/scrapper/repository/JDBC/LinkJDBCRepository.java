package ru.tinkoff.scrapper.repository.JDBC;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Repository;
import ru.tinkoff.scrapper.enyity.LinkEntity;
import ru.tinkoff.scrapper.enyity.mapers.LinkMapper;
import ru.tinkoff.scrapper.repository.LinkRepository;


import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public  class LinkJDBCRepository implements LinkRepository {
    private final LinkMapper mapper = new LinkMapper();

    private final JdbcTemplate template;



    @Override
    public List<LinkEntity> findAll() {
        String sql = "SELECT * FROM link";
        return template.query(sql, mapper);
    }

    @Override
    public List<LinkEntity> findLinkById(Long id) {
        String sql = "SELECT * FROM link where link_id = ?";
        return template.query(sql, mapper, id);
    }

    @Override
    public List<LinkEntity> findAllOrderByDate() {
        String sql = "SELECT * FROM link ORDER BY last_update";
        return template.query(sql, mapper);
    }

    @Override
    public List<LinkEntity> findByURI(String link) {
        String sql = "SELECT * FROM link WHERE link.url = ?";
        return template.query(sql, mapper, link);
    }

    @Override
    public Long add(String link) {
        String sql = "INSERT INTO link (url, last_update) VALUES (?, ?) returning link_id";

        var res = template.query(sql,  new Object[]{link, new Timestamp(System.currentTimeMillis())},
                new SingleColumnRowMapper<>(Long.class));
        return (res.size() > 0)? res.get(0):null;
    }

    @Override
    public List<LinkEntity> findTrackedLinks(Long chatId) {
        final String sql = "select link.link_id, link.url, link.last_update " +
                "from link " +
                "join chat_link on link.link_id = chat_link.link_id " +
                "where chat_link.chat_id = ?";

        return template.query(sql, mapper, chatId);
    }

    @Override
    public void addLinkToChat(Long chatId, Long linkId) {
        String sql = "insert into chat_link (chat_id, link_id) VALUES (?, ?)";
        template.update(sql, chatId, linkId);
    }

    @Override
    public boolean isRecordExistsByLinkIdAndTgChatId(Long linkId, Long chatId) {

        String sql = "SELECT COUNT(*) FROM chat_link WHERE link_id = ? AND chat_id = ?";
        Integer count = template.queryForObject(sql, Integer.class, linkId, chatId);
        return count != null && count > 0;
    }

    @Override
    public void updateLinksDateTimeToNow(List<LinkEntity> links) {
        String sql = "UPDATE link SET last_update = ? WHERE link_id IN (?)";

        template.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
                ps.setLong(2, links.get(i).getLinkId());
            }

            @Override
            public int getBatchSize() {
                return links.size();
            }
        });
    }


}
