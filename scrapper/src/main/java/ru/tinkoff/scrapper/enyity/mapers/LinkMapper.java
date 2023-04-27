package ru.tinkoff.scrapper.enyity.mapers;

import org.springframework.jdbc.core.RowMapper;
import ru.tinkoff.scrapper.enyity.LinkEntity;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LinkMapper implements RowMapper<LinkEntity> {
    @Override
    public LinkEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        LinkEntity link = new LinkEntity();
        link.setLinkId(rs.getLong("link_id"));
        link.setLastUpdateTime(rs.getTimestamp("last_update"));
        try {
            link.setLink(new URI(rs.getString("link")));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        return link;
    }
}


