package ru.tinkoff.scrapper.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.parsers.Parser;
import ru.tinkoff.requasts.GitHabParseResponse;
import ru.tinkoff.requasts.StackOverflowParseResponse;
import ru.tinkoff.scrapper.clients.BotClient;
import ru.tinkoff.scrapper.clients.GitHubClient;
import ru.tinkoff.scrapper.clients.StackOverflowClient;
import ru.tinkoff.scrapper.dto.request.LinkUpdateRequest;
import ru.tinkoff.scrapper.dto.responce.DTOGitHubResponse;
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

        return 0;
    }

    private void stackOverflowResponseCheckUpdate(StackOverflowParseResponse parseResponse, LinkEntity linkEntity) {
        System.out.println("check upgreates on StackOverflow" + linkEntity.getLink());
        var response = stackOverflowClient.getAnswerInfoById(parseResponse.id()).blockFirst();

        StringBuilder description = new StringBuilder();
        if (response == null) description.append("link is not valid\n");

        var chats = chatRepository.findChatByLinkId(linkEntity.getLinkId());
        System.out.println("get all links order traced by this chat" + linkEntity);

        if (chats.size() != 0) {
            LinkUpdateRequest linkUpdateRequest = new LinkUpdateRequest(linkEntity.getLinkId(), linkEntity.getLink(),
                    description.toString(), chats);
            sendMsgToBot(linkUpdateRequest);
        }
    }

    private void gitHabResponseCheckUpdate(GitHabParseResponse parseResponse, LinkEntity linkEntity) {
        System.out.println("check upgreates on  GitHub");
        var events = gitHubClient.getRepo(parseResponse.userName(), parseResponse.repository()).blockFirst();

        if (events != null && events.size() != 0) {
            String description;
            description = showEvents(events, linkEntity.getLastUpdateTime());

            var chats = chatRepository.findChatByLinkId(linkEntity.getLinkId());
            System.out.println("get all links order by date");

            if (chats.size() != 0) {
                LinkUpdateRequest linkUpdateRequest = new LinkUpdateRequest(linkEntity.getLinkId(), linkEntity.getLink(),
                        description, chats);
                sendMsgToBot(linkUpdateRequest);
            }
        }
    }

    private String showEvents(List<DTOGitHubResponse> events, Timestamp lastUpdateTime) {
        StringBuilder sb = new StringBuilder();
        for (var event : events) {
            if (event.created_at().isAfter(OffsetDateTime.ofInstant(lastUpdateTime.toInstant(),
                    ZoneId.of("UTC")))) {
                sb.append(event.created_at()).append('\n')
                        .append(event.type()).append("\n\n");
            }
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
