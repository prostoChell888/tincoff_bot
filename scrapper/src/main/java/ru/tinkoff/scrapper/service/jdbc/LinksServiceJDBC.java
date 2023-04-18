package ru.tinkoff.scrapper.service.jdbc;


import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ru.tincoff.exeptios.BadRequestException;
import ru.tincoff.exeptios.NotFoundException;
import ru.tinkoff.scrapper.dto.request.AddLinkRequest;
import ru.tinkoff.scrapper.dto.responce.link.LinkResponse;
import ru.tinkoff.scrapper.dto.responce.link.ListLinksResponse;
import ru.tinkoff.scrapper.enyity.LinkEntity;
import ru.tinkoff.scrapper.enyity.ChatEntity;
import ru.tinkoff.scrapper.repository.LinkRepository;
import ru.tinkoff.scrapper.repository.TgChatRepository;
import ru.tinkoff.scrapper.service.interfaces.LinkService;
import ru.tinkoff.scrapper.util.validation.anotations.Id;


import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Service
@Validated
public class LinksServiceJDBC implements LinkService {
    public LinkRepository linkRepository;

    public TgChatRepository chatRepository;

    @Override
    @Transactional
    public ListLinksResponse listAll(@Id Long chatId)   {

        if (chatRepository.findById(chatId).size() == 0) {
            throw new NotFoundException("chat with id = " + chatId + " not found");
        }
        List<LinkResponse> linkEntities;
        try {
             linkEntities = entitiesToDtos(linkRepository.findTrackedLinks(chatId));
        } catch ( URISyntaxException e) {
            throw new BadRequestException("");
        }

        return new ListLinksResponse(linkEntities, linkEntities.size());
    }


    @Override
    @Transactional
    public LinkResponse add(@Id Long chatId, @Valid AddLinkRequest request) {

        if (chatRepository.findById(chatId).size() == 0) {
            throw new NotFoundException("chat with id = " + chatId + " not found");
        }

        var linkOptional = linkRepository.findByURI(request.link());
        Integer linkId;
        if ( linkOptional.size() == 0) {
             linkId = linkRepository.add(request.link());
        }else {
            linkId = linkOptional.get(0).getLinkId();
        }

        linkRepository.addLinkToChat(chatId, linkId);

        return new LinkResponse(linkId, request.link());
    }

    @Override
    public void remove(@Id Long chatId)  {
        List<ChatEntity> chatOptional = chatRepository.findById(chatId);
        if (chatOptional.size() == 0) {
            throw new NotFoundException("chat with id = " + chatId + " not found");
        }

        chatRepository.utracedLinks(chatOptional.get(0).getId());
    }

    private List<LinkResponse> entitiesToDtos(List<LinkEntity> linkEntities) throws URISyntaxException {
        List<LinkResponse> linkResponses = new ArrayList<>();

        for (var linkEntity :linkEntities) {
            linkResponses.add(entityToDto(linkEntity));
        }

        return linkResponses;
    }

    private LinkResponse entityToDto(LinkEntity linkEntity) throws URISyntaxException {
        return new LinkResponse(linkEntity.getLinkId(), linkEntity.getLink().toURI());
    }


}
