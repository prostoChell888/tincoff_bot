package ru.tinkoff.parsers;

import ru.tinkoff.requasts.ParseResponse;
import ru.tinkoff.requasts.StackOverflowParseResponse;


import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class StackOverFlowParser extends BaseParser{

    private static Pattern regex = Pattern.compile("^/questions/(\\d+)/.*$");
    @Override
    public ParseResponse parse(URI url) {
        if (url.getHost() != null && url.getHost().equals("stackoverflow.com")){
            Matcher matcher = regex.matcher(url.getPath());
            if (matcher.matches()){
                return new StackOverflowParseResponse( Integer.parseInt(matcher.group(1)));
            } else {
                return null;
            }
        }

        return super.parse(url);
    }


}
