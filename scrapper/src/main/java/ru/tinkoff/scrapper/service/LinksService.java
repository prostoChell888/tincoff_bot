package ru.tinkoff.scrapper.service;


import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.tincoff.exeptios.BadRequestException;
import ru.tincoff.exeptios.NotFoundException;
import ru.tinkoff.scrapper.dto.request.AddLinkRequest;
import ru.tinkoff.scrapper.dto.responce.link.LinkResponse;
import ru.tinkoff.scrapper.dto.responce.link.ListLinksResponse;
import ru.tinkoff.scrapper.util.validation.anotations.Id;


import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

@Service
@Validated
public class LinksService {

    public ResponseEntity<ListLinksResponse> getAllTrackedLinks(@Id Long chatId) {
        if (chatId.intValue() % 2 == 0) throw new BadRequestException("четный id");
        return ResponseEntity.ok(new ListLinksResponse(new ArrayList<>(), 0));
    }

    public ResponseEntity<LinkResponse> addLink(@Id Long chatId,
                                                @Valid AddLinkRequest request) {
        if (chatId.intValue() % 2 == 0) throw new BadRequestException("четный id");


        return ResponseEntity.ok(new LinkResponse(1L, request.link()));
    }

    public ResponseEntity<LinkResponse> delLink(@Id Long chatId
                                                ) throws URISyntaxException {
        if (chatId.intValue() % 3 == 0) throw new BadRequestException(" id кратен 3");
        if (chatId.intValue() % 3 == 1) throw new NotFoundException(" id кратен 2");

        return ResponseEntity.ok(new LinkResponse(1L, new URI("https://docs.spring.io/spring-framework/docs/6.0.0/reference/html/integration.html#rest-http-interface")));
    }
}
