package ru.tinkoff.bot.dto.response.link;

import java.util.List;

public record ListLinksResponse(List<LinkResponse> links, Integer size) {}
