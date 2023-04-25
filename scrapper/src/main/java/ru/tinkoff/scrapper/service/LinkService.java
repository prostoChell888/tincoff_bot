package ru.tinkoff.scrapper.service;

import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import ru.tinkoff.scrapper.dto.request.AddLinkRequest;
import ru.tinkoff.scrapper.dto.responce.link.LinkResponse;
import ru.tinkoff.scrapper.dto.responce.link.ListLinksResponse;
import ru.tinkoff.scrapper.util.validation.anotations.Id;



@Validated
public interface LinkService {

    LinkResponse add( @Id Long tgChatId, @Valid AddLinkRequest request);


    ListLinksResponse listAll(@Id Long tgChatId);

    void remove(@Id Long chatId);
}
