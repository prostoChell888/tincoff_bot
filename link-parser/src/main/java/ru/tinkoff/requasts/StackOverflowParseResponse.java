package ru.tinkoff.requasts;

public record StackOverflowParseResponse(long id) implements ParseResponse {

    @Override
    public String getContentInStr() {
        return Long.toString(id);
    }


}
