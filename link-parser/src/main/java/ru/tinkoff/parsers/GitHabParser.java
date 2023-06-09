package ru.tinkoff.parsers;



import ru.tinkoff.requasts.GitHabParseResponse;
import ru.tinkoff.requasts.ParseResponse;

import java.net.URI;

public final class GitHabParser extends BaseParser  {
    @Override
    public ParseResponse parse(URI url) {
        if (url.getHost() != null && url.getHost().equals("github.com")) {
            String[] userAndRepo = url.getPath().split("/");
            return new GitHabParseResponse(userAndRepo[1], userAndRepo[2]);
        }

        return super.parse(url);
    }
}
