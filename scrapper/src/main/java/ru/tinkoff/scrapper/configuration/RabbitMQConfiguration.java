package ru.tinkoff.scrapper.configuration;


import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {
    @Bean
    DirectExchange directExchange(ApplicationConfig appConfig) {
        return new DirectExchange(appConfig.rabbitMQParam().directExchange(), true, false);
    }

    @Bean
    Queue queue(ApplicationConfig appConfig) {
        return QueueBuilder
                .durable(appConfig.rabbitMQParam().queue())
                .withArgument("x-dead-letter-exchange", appConfig.rabbitMQParam().queue() + ".dlx")
                .withArgument("x-dead-letter-routing-key", appConfig.rabbitMQParam().queue() + ".dlq")
                .build();
    }

    @Bean
    public Binding binding(ApplicationConfig config) {
        return BindingBuilder
                .bind(queue(config))
                .to(directExchange(config))
                .with(config.rabbitMQParam().routingKey());
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
