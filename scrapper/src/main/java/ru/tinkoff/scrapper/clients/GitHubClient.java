package ru.tinkoff.scrapper.clients;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import reactor.core.publisher.Flux;
import ru.tinkoff.scrapper.dto.responce.GitHubResponse;

@HttpExchange(url = "/repos", accept = "application/json", contentType = "application/json")
public interface GitHubClient {
    @GetExchange("{owner}/{repo}")
    Flux<GitHubResponse> getRepo(@PathVariable String owner,
                                 @PathVariable String repo);
}
