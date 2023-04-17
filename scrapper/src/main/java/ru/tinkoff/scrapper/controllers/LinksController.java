package ru.tinkoff.scrapper.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ru.tincoff.exeptios.BadRequestException;
import ru.tincoff.exeptios.NotFoundException;
import ru.tinkoff.scrapper.dto.request.AddLinkRequest;
import ru.tinkoff.scrapper.dto.responce.link.LinkResponse;
import ru.tinkoff.scrapper.dto.responce.link.ListLinksResponse;
import ru.tinkoff.scrapper.service.GitHubService;
import ru.tinkoff.scrapper.service.LinksService;
import ru.tinkoff.scrapper.service.StackOverFlowService;

import java.net.URISyntaxException;

@RestController
@RequestMapping("/links")
@RequiredArgsConstructor
public class LinksController {

    private final StackOverFlowService SOservice;

    private final GitHubService gitHubService;

    private final LinksService linksService;

    @GetMapping
    public ResponseEntity<ListLinksResponse> getAllTrackedLinks(@RequestHeader("Tg-Chat-Id") Long chatId) {
        if (chatId.intValue() % 2 == 0) throw new BadRequestException("четный id");

        return linksService.getAllTrackedLinks(chatId);
    }

    @PostMapping
    public ResponseEntity<LinkResponse> addLink(@RequestHeader("Tg-Chat-Id") Long chatId,
                                                @RequestBody AddLinkRequest request) {
        if (chatId.intValue() % 2 == 0) throw new BadRequestException("четный id");

        return linksService.addLink(chatId, request);
    }

    @DeleteMapping
    public ResponseEntity<LinkResponse> delLink(@RequestHeader("Tg-Chat-Id") Long chatId) throws URISyntaxException {
        if (chatId.intValue() % 3 == 0) throw new BadRequestException("четный id");
//        if (chatId.intValue() % 3 == 1) throw new NotFoundException("четный id");


        return linksService.delLink(chatId);
    }

//    @GetMapping("test")
//    public Object test() {
//
////        return gitHubService.getRepInfo(new GitGubRepRequest("kubernetes", "kubernetes"));
//       return SOservice.getQuestionInfo(49034588L);
//
//    }


}



