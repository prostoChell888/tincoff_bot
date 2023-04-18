package ru.tinkoff.scrapper.service.interfaces;

import ru.tinkoff.scrapper.dto.request.AddLinkRequest;
import ru.tinkoff.scrapper.dto.responce.link.LinkResponse;
import ru.tinkoff.scrapper.dto.responce.link.ListLinksResponse;
import ru.tinkoff.scrapper.util.validation.anotations.Id;

import java.net.URI;
import java.net.URISyntaxException;

public interface LinkService {

    LinkResponse add(Long tgChatId, AddLinkRequest request);


    ListLinksResponse listAll(Long tgChatId) throws URISyntaxException;

    void remove(Long chatId);
}
