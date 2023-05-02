package ru.tinkoff.scrapper.configuration.accessConfigs;


import org.jooq.DSLContext;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.tinkoff.scrapper.repository.JDBC.LinkJDBCRepository;
import ru.tinkoff.scrapper.repository.JDBC.TgChatJDBCRepository;
import ru.tinkoff.scrapper.repository.JOOQ.LinkJOOQRepository;
import ru.tinkoff.scrapper.repository.JOOQ.TgChatJOOQRepository;
import ru.tinkoff.scrapper.repository.LinkRepository;
import ru.tinkoff.scrapper.repository.TgChatRepository;
import ru.tinkoff.scrapper.service.JOOQ.LinksJOOQService;
import ru.tinkoff.scrapper.service.JOOQ.TgChatJOOQService;
import ru.tinkoff.scrapper.service.LinkService;
import ru.tinkoff.scrapper.service.TgChatService;
import ru.tinkoff.scrapper.service.jdbc.LinksJDBCService;
import ru.tinkoff.scrapper.service.jdbc.TgChatJDBCService;

@Configuration
@ConditionalOnProperty(prefix = "scrapper", name = "database-access-type", havingValue = "jooq")
public class JooqAccessConfiguration {



    @Bean
    LinkService linkService(LinkJOOQRepository linkRepository,
                            TgChatJOOQRepository tgChatRepository){
        return new LinksJOOQService(linkRepository, tgChatRepository);
    }

    @Bean
    TgChatService tgChatService(LinkJOOQRepository linkRepository,
                                TgChatJOOQRepository tgChatRepository){
        return new TgChatJOOQService(tgChatRepository, linkRepository );
    }
}
