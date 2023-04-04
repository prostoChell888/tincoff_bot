package ru.tinkoff.requasts;

public record StackOverflowResponse(int id) implements ParseResponse {

    @Override
    public String getContentInStr() {
        return Integer.toString(id);
    }
}
