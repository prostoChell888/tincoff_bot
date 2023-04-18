package ru.tinkoff.scrapper.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.tinkoff.scrapper.enyity.LinkEntity;
import ru.tinkoff.scrapper.enyity.mapers.LinkMapper;


import java.net.URI;
import java.net.URL;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class LinkRepository {
    private final LinkMapper mapper = new LinkMapper();

    @Autowired
    private final JdbcTemplate template;

    public List<LinkEntity> findAll() {
        String sql = "SELECT * FROM link";
        return template.query(sql, mapper);
    }

    public List<LinkEntity> findByURI(URI link) {
        String sql = "SELECT * FROM link where link.link = ?";
        return template.query(sql, mapper, link);
    }

    public Integer add(URI link) {
        String sql = "INSERT INTO link (link) VALUES (?)";
        return template.update(sql, link);
    }

    public Integer removeByLink(URI link) {
        final  String sql = "delete from link where link = ?";
        return template.update(sql, link);
    }

    public Integer removeById(Long id) {
        final  String sql = "delete from link where link_id = ?";
        return template.update(sql, id);
    }


    public Integer updateById(Long id, URL link) {
        final  String sql = "update link set link = ? where link_id = ?";
        return template.update(sql, link, id);
    }

    public List<LinkEntity> findTrackedLinks(Long chatId) {
        final  String sql = "select link_id, link " +
                "from link " +
                "join chat_link on link.link_id = chat_link.link_id " +
                "where chat_link.chat_id = ?";

        return template.query(sql,mapper, chatId);

    }

    public void addLinkToChat(Long chatId, Integer linkId) {
        String sql = "insert into chat_link (chat_id, link_id) VALUES (?, ?)";
        template.update(sql, chatId, linkId);
    }
}
