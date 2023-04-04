package ru.tinkoff.scrapper.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class SchedulerConfig {

    @Bean Long interval(ApplicationConfig config) {
        return config.scheduler().interval().toMillis();
    }
}
