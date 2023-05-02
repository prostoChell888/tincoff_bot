package ru.tinkoff.scrapper.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.scrapper.clients.BotClient;
import ru.tinkoff.scrapper.dto.request.LinkUpdateRequest;

@Service
@RequiredArgsConstructor
public class BotClientUpdateSender implements UpdateSender{

    private final BotClient botClient;
    @Override
    public void send(LinkUpdateRequest update) {
        botClient.sendUpdate(update);
    }
}
