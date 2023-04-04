package ru.tinkoff.requasts;

public sealed interface ParseResponse permits GitHabResponse, StackOverflowResponse {
    String getContentInStr();
}
