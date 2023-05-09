package ru.tinkoff.bot.dto.request;

import jakarta.validation.constraints.NotEmpty;

import java.net.URI;

public record AddLinkRequest(@NotEmpty(message = "link should be not Empty") URI link) {
}
