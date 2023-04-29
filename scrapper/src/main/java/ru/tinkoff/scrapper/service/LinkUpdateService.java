package ru.tinkoff.scrapper.service;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.codec.DecodingException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import ru.tinkoff.parsers.Parser;
import ru.tinkoff.requasts.GitHabParseResponse;
import ru.tinkoff.requasts.StackOverflowParseResponse;
import ru.tinkoff.scrapper.clients.BotClient;
import ru.tinkoff.scrapper.clients.GitHubClient;
import ru.tinkoff.scrapper.clients.StackOverflowClient;
import ru.tinkoff.scrapper.dto.request.LinkUpdateRequest;
import ru.tinkoff.scrapper.dto.responce.DTOGitHubEventResponse;
import ru.tinkoff.scrapper.dto.responce.stackOverflowe.StackOverFlowResponse;
import ru.tinkoff.scrapper.enyity.LinkEntity;
import ru.tinkoff.scrapper.repository.JDBC.LinkJDBCRepository;
import ru.tinkoff.scrapper.repository.JDBC.TgChatJDBCRepository;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class LinkUpdateService implements LinkUpdater {
    private final Parser parser;
    private final GitHubClient gitHubClient;
    private final BotClient botClient;
    private final StackOverflowClient stackOverflowClient;
    private final LinkJDBCRepository linkRepository;

    private final TgChatJDBCRepository chatRepository;
    int COUNT_OF_UPDATES_LINKS = 2;

    @Override
    public int update() {
        System.out.println("get all links from BD order by date");
        var links = linkRepository.findAllOrderByDate();

        List <LinkEntity>list = new ArrayList<>();
        for (int i = 0; i < Math.min(links.size(), COUNT_OF_UPDATES_LINKS); i++) {

            list.add(links.get(i));
            var parseResponse = parser.parse(links.get(i).getLink());

            switch (parseResponse) {
                case StackOverflowParseResponse response -> stackOverflowResponseCheckUpdate(response, links.get(i));
                case GitHabParseResponse response -> gitHabResponseCheckUpdate(response, links.get(i));
            }
        }
        linkRepository.updateLinksDateTimeToNow(list);

        return 0;
    }

    private void stackOverflowResponseCheckUpdate(StackOverflowParseResponse parseResponse, LinkEntity linkEntity) {
        System.out.println("check upgreates on StackOverflow" + linkEntity.getLink());
        StackOverFlowResponse[] response;
        try {
             response = stackOverflowClient.getAnswerInfoById(parseResponse.id()).block();
        } catch (DecodingException | WebClientResponseException e) {
            System.out.println("link (" + linkEntity.getLink() + ") is not valid moree\n");
            response = null;
        }
        if (response != null && response.length != 0
                && response[0].lastActivityDate().isBefore(OffsetDateTime.now())) return;

        sendStackOverflowLinkUpdate(linkEntity, response);
    }

    private void sendStackOverflowLinkUpdate(LinkEntity linkEntity, StackOverFlowResponse[] response) {
        System.out.println("get all links order traced by this chat" + linkEntity);
        var chats = chatRepository.findChatByLinkId(linkEntity.getLinkId());
        if (chats.size() != 0 && response != null) {
            LinkUpdateRequest linkUpdateRequest = new LinkUpdateRequest(
                    linkEntity.getLinkId(),
                    linkEntity.getLink(),
                    getStackOverflowDiscription(linkEntity, response[0]),
                    chats);

            sendMsgToBot(linkUpdateRequest);
        }
    }

    @NotNull
    private static String getStackOverflowDiscription(LinkEntity linkEntity, StackOverFlowResponse response) {
        String description;
        if (response == null ) {
            description = "link ("+ linkEntity.getLink()+") is not valid or is not link to quastion in SO\n";
        }else {
            description = "tackOverflow question ("+ linkEntity.getLink()+ ") has update";
        }

        return description;
    }

    private void gitHabResponseCheckUpdate(GitHabParseResponse parseResponse, LinkEntity linkEntity) {
        System.out.println("check upgreates on GitHub (" + linkEntity.getLink() + ")\n");
        DTOGitHubEventResponse[] events;
        try {
            events = gitHubClient.getRepo(parseResponse.userName(), parseResponse.repository()).block();
        } catch (Throwable e) {
            System.out.println("ошибка: " + e.getMessage());
            System.out.println("link (" + linkEntity.getLink() + ") is not valid moree\n");
            events = null;
        }
        if ( events != null &&  events.length == 0) return;

        sendGitHubLinkUpdate(linkEntity, events);
    }

    private void sendGitHubLinkUpdate(LinkEntity linkEntity, DTOGitHubEventResponse[] events) {
        System.out.println("get all chats order by date\n");
        var chats = chatRepository.findChatByLinkId(linkEntity.getLinkId());
        if (chats.size() != 0) {
            sendMsgToBot(new LinkUpdateRequest(
                    linkEntity.getLinkId(),
                    linkEntity.getLink(),
                    showEvents(events, linkEntity),
                    chats));
        }
    }

    private String showEvents(DTOGitHubEventResponse[] events, LinkEntity lastUpdateTime) {
        StringBuilder sb = new StringBuilder();
        if (events != null && events.length != 0) {
            for (var event : events) {
                if (event.getCreated_at().isAfter(OffsetDateTime.ofInstant(lastUpdateTime.getLastUpdateTime()
                                .toInstant(), ZoneId.of("UTC")))) {
                    sb.append(event.getCreated_at()).append('\n').append(event.getType()).append("\n\n");
                }
            }
        } else if (events == null) {
            sb.append("(Warn)  link (").append(lastUpdateTime.getLink()).append(") is not walid, unsubscribe from it\n");
        }

        return sb.toString();
    }

    private void sendMsgToBot(LinkUpdateRequest response) {
        try {
            botClient.sendUpdate(response);
            System.out.println("send msg to bot");
        } catch (Exception e) {
            System.out.println("Error updating link " + response.getUrl() + e.getMessage());
        }
    }
}
