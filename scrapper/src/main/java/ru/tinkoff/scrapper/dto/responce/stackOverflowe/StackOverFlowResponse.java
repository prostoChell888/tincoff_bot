package ru.tinkoff.scrapper.dto.responce.stackOverflowe;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.net.URI;
import java.time.OffsetDateTime;

public record StackOverFlowResponse(
        String title,
        String[] tags,
        @JsonProperty("is_answered")
        boolean isAnswered,
        @JsonProperty("view_count")
        int viewCount,
        @JsonProperty("creation_date")
        OffsetDateTime creationDate,
        URI link
        ) {}
