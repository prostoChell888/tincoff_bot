package ru.tinkoff.scrapper.dto.responce;

import java.util.List;

public record DTOGitHubListEventResponse(List<DTOGitHubEventResponse> event) {
}
