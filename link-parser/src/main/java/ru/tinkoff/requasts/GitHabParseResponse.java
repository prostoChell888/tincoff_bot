package ru.tinkoff.requasts;

public record GitHabParseResponse(String userName, String repository) implements ParseResponse {
    @Override
    public String   getContentInStr() {
        return userName + "/" + repository;
    }


    @Override
    public String userName() {
        return userName;
    }

    @Override
    public String repository() {
        return repository;
    }
}
