package ru.tinkoff.scrapper.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record AddLinkRequest(@NotEmpty(message = "link should be not Empty") String link) {}
