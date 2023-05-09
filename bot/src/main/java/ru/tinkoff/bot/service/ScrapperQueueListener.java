package ru.tinkoff.bot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import ru.tinkoff.bot.dto.request.LinkUpdateRequest;


@RabbitListener(queues = "${bot.rabbitMQParam.queue}")
@RequiredArgsConstructor
@Service
public class ScrapperQueueListener {

    private final BotService botService;

    @RabbitHandler
    public void receiver(LinkUpdateRequest update) {
        System.out.println("=====get msq from Queue=====");
        System.out.println(update);
        botService.update(update);
    }
}
