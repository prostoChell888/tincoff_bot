package ru.tinkoff.scrapper.service.JPA;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tincoff.exeptios.NotFoundException;
import ru.tinkoff.scrapper.dto.request.AddLinkRequest;
import ru.tinkoff.scrapper.dto.responce.link.LinkResponse;
import ru.tinkoff.scrapper.dto.responce.link.ListLinksResponse;
import ru.tinkoff.scrapper.enyity.JPA.JPALinkEntity;
import ru.tinkoff.scrapper.repository.JPA.LinkJPARepository;
import ru.tinkoff.scrapper.repository.JPA.TgChatJPARepository;
import ru.tinkoff.scrapper.service.LinkService;

import java.util.ArrayList;
import java.util.List;



@RequiredArgsConstructor
public class LinksJpaService implements LinkService {

    private final LinkJPARepository linkRepository;
    private final TgChatJPARepository chatRepository;


    @Override
    public LinkResponse add(Long tgChatId, AddLinkRequest request) {
        var chatOptional = chatRepository.findByTgChatId(tgChatId);
        if (chatOptional.isEmpty() ){
            throw new NotFoundException("Чат с id" + tgChatId + " не найден");
        }
        var chat = chatOptional.get();

        var linkOptional = linkRepository.findByLink(request.link()) ;
        var link = linkOptional.get();
        if (linkOptional.isEmpty()) {
            link = new JPALinkEntity();
            link.setLink(request.link());
            link = linkRepository.save(link);
        }
        chat.getLinks().add(link);
        chatRepository.save(chat);

        return new LinkResponse(link.getLinkId(), request.link());

    }

    @Override
    public ListLinksResponse listAll(Long tgChatId) {
        var chatOption = chatRepository.findByTgChatId(tgChatId);
        if (chatOption.isEmpty() ){
            throw new NotFoundException("Чат с id" + tgChatId + " не найден");
        }
        var links = linkRepository.findAllByChats(chatOption.get());

        List<LinkResponse> chatIds = new ArrayList<>();
        for (var link:links) {
            chatIds.add(new LinkResponse(link.getLinkId(), link.getLink()));
        }
        return new ListLinksResponse(chatIds, chatIds.size());
    }

    @Override
    public void remove(Long chatId) {
        var chatOption = chatRepository.findById(chatId);
        if (chatOption.isEmpty() ){
            throw new NotFoundException("Чат с id" + chatId + " не найден");
        }

        chatRepository.deleteById(chatId);


    }
}
