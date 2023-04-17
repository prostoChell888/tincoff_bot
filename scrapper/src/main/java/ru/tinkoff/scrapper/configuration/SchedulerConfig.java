package ru.tinkoff.scrapper.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SchedulerConfig {

    @Bean Long interval(ApplicationConfig config) {
        return config.scheduler().interval().toMillis();
    }
}
