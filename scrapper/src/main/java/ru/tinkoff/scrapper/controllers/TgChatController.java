package ru.tinkoff.scrapper.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.scrapper.service.jdbc.TgChatServiceJDBC;

@RestController
@RequestMapping("/tg-chat")
@RequiredArgsConstructor
public class TgChatController {

    private final TgChatServiceJDBC tgChatService;


    @GetMapping("check")
    public String check() {
        return "ku";
    }

    @PostMapping("{id}")
    public void addChat(@PathVariable Long id) {
        tgChatService.add(id);
    }

    @DeleteMapping("{id}")
    public void delChat(@PathVariable Long id) {
        tgChatService.removeByChatId(id);
    }
}
