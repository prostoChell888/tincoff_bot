package ru.tinkoff.scrapper.service.JOOQ;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ru.tincoff.exeptios.BadRequestException;
import ru.tincoff.exeptios.NotFoundException;
import ru.tinkoff.scrapper.dto.request.AddLinkRequest;
import ru.tinkoff.scrapper.dto.responce.link.LinkResponse;
import ru.tinkoff.scrapper.dto.responce.link.ListLinksResponse;
import ru.tinkoff.scrapper.enyity.ChatEntity;
import ru.tinkoff.scrapper.enyity.LinkEntity;
import ru.tinkoff.scrapper.repository.JDBC.LinkJDBCRepository;
import ru.tinkoff.scrapper.repository.JDBC.TgChatJDBCRepository;
import ru.tinkoff.scrapper.repository.JOOQ.LinkJOOQRepository;
import ru.tinkoff.scrapper.repository.JOOQ.TgChatJOOQRepository;
import ru.tinkoff.scrapper.service.LinkService;
import ru.tinkoff.scrapper.util.validation.anotations.Id;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;



@Validated
@RequiredArgsConstructor
public class LinksJOOQService implements LinkService {

    private final LinkJOOQRepository linkJDBCRepository;

    private final TgChatJOOQRepository chatRepository;


    @Override
    @Transactional
    public ListLinksResponse listAll(@Id Long chatId) {

        if (chatRepository.findByTgChatId(chatId).size() == 0) {
            throw new NotFoundException("chat with id = " + chatId + " not found");
        }
        List<LinkResponse> linkEntities;
        try {
            linkEntities = entitiesToDtos(linkJDBCRepository.findTrackedLinks(chatId));
        } catch (URISyntaxException e) {
            throw new BadRequestException("");
        }

        return new ListLinksResponse(linkEntities, linkEntities.size());
    }


    @Override
    public LinkResponse add(@Id Long tgChatId, @Valid AddLinkRequest request) {
        var chatIdOptional = chatRepository.findIdByTgChatId(tgChatId);
        if (chatIdOptional.size() == 0) {
            throw new NotFoundException("chat with id = " + tgChatId + " not found");
        }

        System.out.println(request.link().toString());
        var linkOptional = linkJDBCRepository.findByURI(request.link().toString());
        Long linkId;
        if (linkOptional.size() == 0)
            linkId = linkJDBCRepository.add(request.link().toString());
        else
            linkId = linkOptional.get(0).getLinkId();

        if (linkJDBCRepository.isRecordExistsByLinkIdAndTgChatId(linkId, chatIdOptional.get(0))) {
            throw new BadRequestException("пользователь уже подписан на отслеживание данной ссылки ");
        }
        linkJDBCRepository.addLinkToChat(chatIdOptional.get(0), linkId);
        return new LinkResponse(linkId, request.link());
    }

    @Override
    public void remove(@Id Long chatId) {
        List<ChatEntity> chatOptional = chatRepository.findByTgChatId(chatId);
        if (chatOptional.size() == 0) {
            throw new NotFoundException("chat with id = " + chatId + " not found");
        }

        chatRepository.utracedLinks(chatOptional.get(0).getId());
    }

    private List<LinkResponse> entitiesToDtos(List<LinkEntity> linkEntities) throws URISyntaxException {
        List<LinkResponse> linkResponses = new ArrayList<>();

        for (var linkEntity : linkEntities) {
            linkResponses.add(entityToDto(linkEntity));
        }

        return linkResponses;
    }

    private LinkResponse entityToDto(LinkEntity linkEntity) throws URISyntaxException {
        return new LinkResponse(linkEntity.getLinkId(), linkEntity.getLink());
    }


}
