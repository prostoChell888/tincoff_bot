package ru.tinkoff.parsers;

import ru.tinkoff.requasts.ParseResponse;

import java.net.URI;
import java.net.URISyntaxException;

public sealed abstract class BaseParser implements Parser permits StackOverFlowParser, GitHabParser{
    private Parser handler;


    public Parser getHandler() {
        return handler;
    }

    @Override
    public void setHandler(Parser handler) {
        this.handler = handler;
    }

    @Override
    public ParseResponse parse(URI url) {
        if (handler != null){
            return handler.parse(url);
        }
        return null;
    }

    @Override
    public ParseResponse parse(String url) {
        URI uri;
        try {
            uri = new URI(url);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        return parse(uri);
    }



}
