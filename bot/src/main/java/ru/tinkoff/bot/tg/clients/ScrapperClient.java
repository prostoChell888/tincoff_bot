package ru.tinkoff.bot.tg.clients;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.tinkoff.bot.configuration.ApplicationConfig;
import ru.tinkoff.bot.dto.request.AddLinkRequest;
import ru.tinkoff.bot.dto.response.link.LinkResponse;
import ru.tinkoff.bot.dto.response.link.ListLinksResponse;


@Component
public class ScrapperClient {
    private final WebClient webClient;

    public ScrapperClient(ApplicationConfig config) {
        this.webClient = WebClient.create(config.baseUrl());
    }

    public LinkResponse postLink(Long chatId, AddLinkRequest request) {
        Mono<AddLinkRequest> requestMono = Mono.just(request);

        return webClient.post()
                .uri("links")
                .header("Tg-Chat-Id", String.valueOf(chatId))
                .body(BodyInserters.fromPublisher(requestMono, AddLinkRequest.class))
                .retrieve().
                bodyToMono(LinkResponse.class).block();
    }


    public ListLinksResponse getListLinks(Long chatId) {
        return webClient.get()
                .uri("links")
                .header("Tg-Chat-Id", String.valueOf(chatId))
                .retrieve().
                bodyToMono(ListLinksResponse.class).block();
    }

    public LinkResponse deleteLink(Long chatId) {
        return webClient.delete()
                .uri("links")
                .header("Tg-Chat-Id", String.valueOf(chatId))
                .retrieve()
                .bodyToMono(LinkResponse.class)
                .block();
    }

    public void postChatId(Long chatId) {
        webClient.post()
                .uri("tg-chat/{chatId}", chatId)
                .retrieve();
    }



    public void deleteChatId(Long chatId) {
        webClient.delete()
                .uri("tg-chat/{chatId}", chatId)
                .retrieve();
    }
}




