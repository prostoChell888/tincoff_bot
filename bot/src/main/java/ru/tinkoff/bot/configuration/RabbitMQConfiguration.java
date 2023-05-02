package ru.tinkoff.bot.configuration;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

    @Bean
    DirectExchange directExchange(ApplicationConfig config) {
        return new DirectExchange(config.rabbitMQParam().directExchange());
    }

    @Bean
    public Queue queue(ApplicationConfig config) {
        return new Queue(config.rabbitMQParam().queue());
    }

    @Bean
    public Binding binding(ApplicationConfig config) {
        return BindingBuilder.bind(queue(config))
                .to(directExchange(config))
                .with(config.rabbitMQParam().routingKey());
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
