package ru.tinkoff.bot.configuration;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.ClassMapper;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.bot.dto.request.LinkUpdateRequest;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfiguration {

    @Bean
    DirectExchange deadLetterDirectExchange(ApplicationConfig config) {
        return new DirectExchange(config.rabbitMQParam().directExchange() + ".dlx", true, false);
    }

    @Bean
    public Queue deadLetterQueue(ApplicationConfig config) {
        return new Queue(config.rabbitMQParam().queue() + ".dlq");
    }

    @Bean
    public Binding deadLetterBinding(ApplicationConfig config) {
        return BindingBuilder
                .bind(deadLetterQueue(config))
                .to(deadLetterDirectExchange(config))
                .with(config.rabbitMQParam().routingKey());
    }


    @Bean
    public ClassMapper classMapper() {
        Map<String, Class<?>> mappings = new HashMap<>();
        mappings.put("ru.tinkoff.scrapper.dto.request.LinkUpdateRequest", LinkUpdateRequest.class);

        DefaultClassMapper classMapper = new DefaultClassMapper();
        classMapper.setTrustedPackages("ru.tinkoff.bot.dto.request.*");

        classMapper.setIdClassMapping(mappings);
        return classMapper;
    }

    @Bean
    public MessageConverter jsonMessageConverter(ClassMapper classMapper) {
        Jackson2JsonMessageConverter jsonConverter = new Jackson2JsonMessageConverter();
        jsonConverter.setClassMapper(classMapper);
        return jsonConverter;
    }
}
