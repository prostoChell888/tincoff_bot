package ru.tinkoff.bot.service;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.tinkoff.bot.dto.request.LinkUpdateRequest;
import ru.tinkoff.bot.tg.LinkTelegramBot;

//@Validated
@Service
@AllArgsConstructor
public class BotService {
    private final LinkTelegramBot linkTelegramBot;

    public void update(@Valid LinkUpdateRequest request) {

        String msg = "Обновление по ссылке: " + request.getUrl() + "\n" + request.getDescription();

        for (var tgChatId:request.getTgChatIds()) {
            linkTelegramBot.sendCommand(tgChatId, msg);
        }
    }
}
