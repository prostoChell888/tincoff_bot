package ru.tinkoff.requasts;

public record GitHabResponse(String userName, String repository) implements ParseResponse{
    @Override
    public String   getContentInStr() {
        return userName + "/" + repository;
    }
}
