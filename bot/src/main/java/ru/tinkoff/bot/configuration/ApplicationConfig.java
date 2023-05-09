package ru.tinkoff.bot.configuration;


import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "bot", ignoreUnknownFields = false)
public record ApplicationConfig(@NotNull String test,
                                @NotNull String token,
                                @NotNull String baseUrl,
                                @NotNull RabbitMQParam rabbitMQParam) {

    public record RabbitMQParam(String directExchange, String queue, String routingKey) {
    }


}
