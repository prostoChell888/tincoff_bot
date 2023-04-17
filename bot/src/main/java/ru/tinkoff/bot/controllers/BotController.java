package ru.tinkoff.bot.controllers;


import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.bot.tg.clients.ScrapperClient;
import ru.tinkoff.bot.dto.request.AddLinkRequest;
import ru.tinkoff.bot.dto.request.LinkUpdateRequest;
import ru.tinkoff.bot.service.BotService;

@RestController
@RequestMapping("updates")
@RequiredArgsConstructor
public class BotController {
    private final BotService botService;

    @PostMapping
    @Operation(
            tags = {"links"},
            operationId = "updateLink",
            summary = "кратокое описани",
            description = "подробное описание"
    )
    public void update(@RequestBody LinkUpdateRequest request) {
        botService.update(request);
    }



    //todo проверка работоспособности (удалить)
    private final ScrapperClient scrapperClient;

    //todo проверка работоспособности (удалить)
    @PostMapping("check")
    public void check(@RequestBody AddLinkRequest request) {
        scrapperClient.deleteChatId(5L);
    }
}
