package ru.tinkoff.scrapper.clients;


import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import ru.tinkoff.scrapper.dto.request.LinkUpdateRequest;

@HttpExchange( accept = "application/json", contentType = "application/json")
public interface BotClient {

    @PostExchange(url = "/updates")
    void sendUpdate(@RequestBody LinkUpdateRequest linkUpdate);
}
