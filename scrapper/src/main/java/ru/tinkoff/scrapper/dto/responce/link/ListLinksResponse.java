package ru.tinkoff.scrapper.dto.responce.link;

import java.util.List;

public record ListLinksResponse(List<LinkResponse> links, Integer size) {}
