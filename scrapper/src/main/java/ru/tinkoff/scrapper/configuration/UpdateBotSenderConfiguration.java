package ru.tinkoff.scrapper.configuration;


import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.scrapper.clients.BotClient;
import ru.tinkoff.scrapper.service.BotClientUpdateSender;
import ru.tinkoff.scrapper.service.UpdateSender;

@Configuration
@ConditionalOnProperty(prefix = "scrapper", name = "useQueue", havingValue = "false")
public class UpdateBotSenderConfiguration {

    @Bean
    public UpdateSender updateSender(BotClient botClient) {
        return new BotClientUpdateSender(botClient);
    }
}
