package ru.tinkoff.parsers;

import ru.tinkoff.requasts.ParseResponse;

import java.net.URI;
import java.net.URL;
import java.util.logging.Handler;

public sealed interface Parser permits BaseParser {

    ParseResponse parse(String url);

    ParseResponse parse(URI url);

     void setHandler(Parser handler);


}
