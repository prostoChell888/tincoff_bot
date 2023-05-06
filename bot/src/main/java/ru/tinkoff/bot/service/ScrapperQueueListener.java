package ru.tinkoff.bot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import ru.tinkoff.bot.dto.request.LinkUpdateRequest;


@RabbitListener(queues = "${scrapper.rabbitMQParam.queue}")
@RequiredArgsConstructor
public class ScrapperQueueListener {

    private final BotService botService;

    @RabbitHandler
    public void receiver(LinkUpdateRequest update) {
        botService.update(update);
    }
}
