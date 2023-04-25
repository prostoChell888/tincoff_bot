package ru.tinkoff.requasts;

public sealed interface ParseResponse permits GitHabParseResponse, StackOverflowParseResponse {
    String getContentInStr();
}
