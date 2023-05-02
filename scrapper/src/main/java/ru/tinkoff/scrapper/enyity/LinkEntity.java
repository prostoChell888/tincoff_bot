package ru.tinkoff.scrapper.enyity;



import lombok.AllArgsConstructor;
import lombok.Data;

import java.net.URI;
import java.net.URL;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class LinkEntity {
    private Long linkId;

    private URI link;

    private Timestamp lastUpdateTime;
}
