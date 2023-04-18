package ru.tinkoff.scrapper.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ru.tincoff.exeptios.BadRequestException;
import ru.tinkoff.scrapper.dto.request.AddLinkRequest;
import ru.tinkoff.scrapper.dto.responce.link.LinkResponse;
import ru.tinkoff.scrapper.dto.responce.link.ListLinksResponse;
import ru.tinkoff.scrapper.service.jdbc.LinksServiceJDBC;

import java.net.URISyntaxException;

@RestController
@RequestMapping("/links")
@RequiredArgsConstructor
public class LinksController {

    private final LinksServiceJDBC linksService;

    @GetMapping
    public ListLinksResponse getAllTrackedLinks(@RequestHeader("Tg-Chat-Id") Long chatId) {

        return linksService.listAll(chatId);
    }

    @PostMapping
    public LinkResponse addLink(@RequestHeader("Tg-Chat-Id") Long chatId,
                                @RequestBody AddLinkRequest request) {

        return linksService.add(chatId, request);
    }

    @DeleteMapping
    public void delLink(@RequestHeader("Tg-Chat-Id") Long chatId) throws URISyntaxException {

         linksService.remove(chatId);
    }
}



