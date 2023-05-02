package ru.tinkoff.scrapper.configuration;


import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.validation.annotation.Validated;
import ru.tinkoff.scrapper.util.Scheduler;

@Validated
@ConfigurationProperties(prefix = "scrapper", ignoreUnknownFields = false)
@EnableScheduling
public record ApplicationConfig(@NotNull String test,
                                @NotNull Scheduler scheduler,
                                @NotNull AccessType databaseAccessType,
                                @NotNull RabbitMQParam rabbitMQParam) {
    public enum AccessType {
        JDBC, JPA,
        JOOQ
    }

    public  record RabbitMQParam(String directExchange,
                                 String queue,
                                 String routingKey){}

}