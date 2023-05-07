package ru.tinkoff.scrapper.configuration;


import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.scrapper.service.ScrapperQueueProducer;
import ru.tinkoff.scrapper.service.UpdateSender;

@Configuration
@ConditionalOnProperty(prefix = "scrapper", name = "useQueue", havingValue = "true")
public class UpdateRabbitSenderConfiguration {

    @Bean
    public ScrapperQueueProducer updateSender(RabbitTemplate template, ApplicationConfig config) {
        return new ScrapperQueueProducer(template, config);
    }
}
