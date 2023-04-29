package ru.tinkoff.scrapper.configuration.accessConfigs;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.tinkoff.scrapper.repository.JDBC.LinkJDBCRepository;
import ru.tinkoff.scrapper.repository.JDBC.TgChatJDBCRepository;
import ru.tinkoff.scrapper.repository.LinkRepository;
import ru.tinkoff.scrapper.repository.TgChatRepository;
import ru.tinkoff.scrapper.service.LinkService;
import ru.tinkoff.scrapper.service.jdbc.LinksJDBCService;
import ru.tinkoff.scrapper.service.jdbc.TgChatJDBCService;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jdbc")
public class JdbcAccessConfiguration {



    @Bean
    LinkService linkService(LinkJDBCRepository linkRepository,
                            TgChatJDBCRepository tgChatRepository) {
        return new LinksJDBCService(linkRepository, tgChatRepository);
    }

    @Bean
    TgChatJDBCService tgChatService(LinkJDBCRepository linkRepository,
                                    TgChatJDBCRepository tgChatRepository) {
        return new TgChatJDBCService(tgChatRepository, linkRepository);
    }
}
