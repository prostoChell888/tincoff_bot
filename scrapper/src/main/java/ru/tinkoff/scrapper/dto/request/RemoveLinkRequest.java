package ru.tinkoff.scrapper.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record RemoveLinkRequest(
        @NotEmpty(message = "link should be not Empty")
        String link) {
}
