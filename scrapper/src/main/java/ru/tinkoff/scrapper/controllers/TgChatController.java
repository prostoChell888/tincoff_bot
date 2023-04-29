package ru.tinkoff.scrapper.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.scrapper.service.TgChatService;
import ru.tinkoff.scrapper.service.jdbc.TgChatJDBCService;

@RestController
@RequestMapping("/tg-chat")
@RequiredArgsConstructor
public class TgChatController {
    private final TgChatService tgChatService;

    @PostMapping("{id}")
    public void register(@PathVariable Long id) {
        tgChatService.register(id);
    }

    @DeleteMapping("{id}")
    public void unregister(@PathVariable Long id) {
        tgChatService.unregister(id);
    }
}
