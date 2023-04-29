package ru.tinkoff.scrapper.clients;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.tinkoff.scrapper.dto.DTOGitHubEventResponses;
import ru.tinkoff.scrapper.dto.responce.DTOGitHubEventResponse;
import ru.tinkoff.scrapper.dto.responce.DTOGitHubListEventResponse;

import java.util.List;

@HttpExchange(
        url = "repos/",
        accept = MediaType.APPLICATION_JSON_VALUE,
        contentType = MediaType.APPLICATION_JSON_VALUE
)
public interface GitHubClient {
    @GetExchange("{owner}/{repo}/events")
    Mono<DTOGitHubEventResponse[]> getRepo(@PathVariable String owner,
                                          @PathVariable String repo);
}
