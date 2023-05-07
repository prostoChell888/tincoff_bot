package ru.tinkoff.scrapper.service;


import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.tinkoff.scrapper.configuration.ApplicationConfig;
import ru.tinkoff.scrapper.dto.request.LinkUpdateRequest;


@RequiredArgsConstructor
public class ScrapperQueueProducer implements UpdateSender {

    final RabbitTemplate template;
    final ApplicationConfig appConfig;

    @Override
    public void send(LinkUpdateRequest update) {
        template.convertAndSend(appConfig.rabbitMQParam().directExchange(),
                appConfig.rabbitMQParam().routingKey(),
                update);
    }
}
