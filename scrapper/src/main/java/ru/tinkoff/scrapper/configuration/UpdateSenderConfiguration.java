package ru.tinkoff.scrapper.configuration;


import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "scrapper", name = "", havingValue = "jdbc")
public class UpdateSenderConfiguration {


}
