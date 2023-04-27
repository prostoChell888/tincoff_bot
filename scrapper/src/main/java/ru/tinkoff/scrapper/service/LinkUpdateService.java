package ru.tinkoff.scrapper.service;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.codec.DecodingException;
import org.springframework.stereotype.Service;
import ru.tinkoff.parsers.Parser;
import ru.tinkoff.requasts.GitHabParseResponse;
import ru.tinkoff.requasts.StackOverflowParseResponse;
import ru.tinkoff.scrapper.clients.BotClient;
import ru.tinkoff.scrapper.clients.GitHubClient;
import ru.tinkoff.scrapper.clients.StackOverflowClient;
import ru.tinkoff.scrapper.dto.request.LinkUpdateRequest;
import ru.tinkoff.scrapper.dto.responce.DTOGitHubResponse;
import ru.tinkoff.scrapper.dto.responce.stackOverflowe.StackOverFlowListResponse;
import ru.tinkoff.scrapper.enyity.LinkEntity;
import ru.tinkoff.scrapper.repository.JDBC.LinkJDBCRepository;
import ru.tinkoff.scrapper.repository.JDBC.TgChatJDBCRepository;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;

import static java.time.ZoneOffset.UTC;


@Service
@RequiredArgsConstructor
public class LinkUpdateService implements LinkUpdater {
    private final Parser parser;
    private final GitHubClient gitHubClient;
    private final BotClient botClient;
    private final StackOverflowClient stackOverflowClient;
    private final LinkJDBCRepository linkJDBCRepository;
    private final TgChatJDBCRepository chatRepository;
    int COUNT_OF_UPDATES_LINKS = 2;

    @Override
    public int update() {
        System.out.println("get all links from BD order by date");
        var links = linkJDBCRepository.findAllOrderByDate();

        for (int i = 0; i < Math.min(links.size(), COUNT_OF_UPDATES_LINKS); i++) {
            var parseResponse = parser.parse(links.get(i).getLink());

            switch (parseResponse) {
                case StackOverflowParseResponse response -> stackOverflowResponseCheckUpdate(response, links.get(i));
                case GitHabParseResponse response -> gitHabResponseCheckUpdate(response, links.get(i));
            }
        }
        linkJDBCRepository.updateLinksDate(links, OffsetDateTime.now());

        return 0;
    }

    private void stackOverflowResponseCheckUpdate(StackOverflowParseResponse parseResponse, LinkEntity linkEntity) {
        System.out.println("check upgreates on StackOverflow" + linkEntity.getLink());
        var response = stackOverflowClient.getAnswerInfoById(parseResponse.id()).blockFirst();
        if (response != null && response.items().length != 0
                && response.items()[0].lastActivityDate().isBefore(OffsetDateTime.now())) return;

        String description = getStackOverflowDiscription(linkEntity, response);
        sendStackOverflowLinkUpdate(linkEntity, description);
    }

    private void sendStackOverflowLinkUpdate(LinkEntity linkEntity, String description) {
        System.out.println("get all links order traced by this chat" + linkEntity);
        var chats = chatRepository.findChatByLinkId(linkEntity.getLinkId());
        if (chats.size() != 0) {
            LinkUpdateRequest linkUpdateRequest = new LinkUpdateRequest(
                    linkEntity.getLinkId(),
                    linkEntity.getLink(),
                    description,
                    chats);
            sendMsgToBot(linkUpdateRequest);
        }
    }

    @NotNull
    private static String getStackOverflowDiscription(LinkEntity linkEntity, StackOverFlowListResponse response) {
        String description;
        if (response == null || response.items().length == 0) {
            description = "link ("+ linkEntity.getLink()+") is not valid\n";
        }else {
            description = "StackOverflow question ("+ linkEntity.getLink()+
                    ") has apdeated" + response.items()[0].lastActivityDate();
        }
        return description;
    }

    private void gitHabResponseCheckUpdate(GitHabParseResponse parseResponse, LinkEntity linkEntity) {
        System.out.println("check upgreates on GitHub (" + linkEntity.getLink() + ")\n");
        List<DTOGitHubResponse> events;
        try {
            events = gitHubClient.getRepo(parseResponse.userName(), parseResponse.repository()).blockFirst();
        } catch (DecodingException e) {
            System.out.println("link (" + linkEntity.getLink() + ") is not valid moree\n");
            events = null;
        }

        if (events != null && events.size() == 0) return;

        sendgitHubLinkUpdate(linkEntity, events);
    }

    private void sendgitHubLinkUpdate(LinkEntity linkEntity, List<DTOGitHubResponse> events) {
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

    private String showEvents(List<DTOGitHubResponse> events, LinkEntity lastUpdateTime) {
        StringBuilder sb = new StringBuilder("qq");
        if (events != null && events.size() != 0) {
            for (var event : events) {
                if (event.created_at().isAfter(OffsetDateTime.ofInstant(lastUpdateTime.getLastUpdateTime().toInstant(),
                        ZoneId.of("UTC")))) {
                    sb.append(event.created_at()).append('\n')
                            .append(event.type()).append("\n\n");
                }
            }
        } else if (events == null) {
            sb.append("(Warn)  link (")
                    .append(lastUpdateTime.getLink()).append(") is not walid, unsubscribe from it\n");
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
