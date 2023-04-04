package ru.tinkoff.scrapper.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.scrapper.service.TgChatService;

@RestController
@RequestMapping("/tg-chat")
@RequiredArgsConstructor
public class TgChatController {

    private final TgChatService linksService;

    @PostMapping("{id}")
    public void addChat(@PathVariable Long id) {
        linksService.addChat(id);
    }

    @DeleteMapping("{id}")
    public void delChat(@PathVariable Long id) {
        linksService.delChat(id);
    }


}
