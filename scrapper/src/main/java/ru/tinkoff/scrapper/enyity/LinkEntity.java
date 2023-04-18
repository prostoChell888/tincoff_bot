package ru.tinkoff.scrapper.enyity;



import lombok.Data;

import java.net.URI;
import java.net.URL;

@Data
public class LinkEntity {
    private Integer linkId;

    private URL link;
}
