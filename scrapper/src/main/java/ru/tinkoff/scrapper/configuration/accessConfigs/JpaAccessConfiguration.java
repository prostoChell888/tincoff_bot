package ru.tinkoff.scrapper.configuration.accessConfigs;


import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.scrapper.repository.JOOQ.LinkJOOQRepository;
import ru.tinkoff.scrapper.repository.JOOQ.TgChatJOOQRepository;
import ru.tinkoff.scrapper.repository.JPA.LinkJPARepository;
import ru.tinkoff.scrapper.repository.JPA.TgChatJPARepository;
import ru.tinkoff.scrapper.service.JOOQ.LinksJOOQService;
import ru.tinkoff.scrapper.service.JOOQ.TgChatJOOQService;
import ru.tinkoff.scrapper.service.JPA.LinksJpaService;
import ru.tinkoff.scrapper.service.JPA.TgChatJpaService;
import ru.tinkoff.scrapper.service.LinkService;
import ru.tinkoff.scrapper.service.TgChatService;

@Configuration
@ConditionalOnProperty(prefix = "scrapper", name = "database-access-type", havingValue = "jpa")
public class JpaAccessConfiguration {

    @Bean
    LinkService linkService(LinkJPARepository linkRepository,
                            TgChatJPARepository tgChatRepository){
        return new LinksJpaService(linkRepository, tgChatRepository);
    }

    @Bean
    TgChatService tgChatService(TgChatJPARepository tgChatRepository){
        return new TgChatJpaService(tgChatRepository );
    }
}
