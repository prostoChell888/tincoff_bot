package ru.tinkoff.bot.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record GitGubRepRequest(
        @NotBlank
        @NotNull
        String owner,
        @NotBlank
        @NotNull
        String repo
        ) {
}
