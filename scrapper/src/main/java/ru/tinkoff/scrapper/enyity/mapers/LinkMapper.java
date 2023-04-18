package ru.tinkoff.scrapper.enyity.mapers;

import org.springframework.jdbc.core.RowMapper;
import ru.tinkoff.scrapper.enyity.LinkEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LinkMapper implements RowMapper<LinkEntity> {
    @Override
    public LinkEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        LinkEntity link = new LinkEntity();
        link.setLinkId(rs.getInt("link_id"));
        link.setLink(rs.getURL("link"));

        return link;
    }

}


