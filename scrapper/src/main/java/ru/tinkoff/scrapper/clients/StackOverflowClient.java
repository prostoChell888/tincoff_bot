package ru.tinkoff.scrapper.clients;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import reactor.core.publisher.Flux;
import ru.tinkoff.scrapper.dto.responce.stackOverflowe.StackOverFlowListResponse;


@HttpExchange(url = "/questions", accept = "application/json", contentType = "application/json")
public interface StackOverflowClient {
    @GetExchange("{id}?site=stackoverflow")
    Flux<StackOverFlowListResponse> getAnswerInfoById(@PathVariable Long id);
}
