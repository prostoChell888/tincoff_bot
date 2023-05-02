package ru.tinkoff.scrapper.dto.responce;

import lombok.NonNull;

import java.time.OffsetDateTime;

public record DTOGitHubResponse(@NonNull String type,
                                @NonNull OffsetDateTime created_at) {
}
