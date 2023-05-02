package ru.tinkoff.scrapper.service;


import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.scrapper.clients.BotClient;
import ru.tinkoff.scrapper.configuration.ApplicationConfig;

@Configuration
@ConditionalOnProperty(prefix = "scrapper", name = "useQueue", havingValue = "false")
public class UpdateRabbitSenderConfiguration {

    @Bean
    public UpdateSender updateSender(RabbitTemplate template, ApplicationConfig config) {
        return new ScrapperQueueProducer(template, config);
    }
}
