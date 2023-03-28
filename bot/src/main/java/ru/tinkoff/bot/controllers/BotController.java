package ru.tinkoff.bot.controllers;


import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    public void update(@RequestBody LinkUpdateRequest request){
        botService.update(request);
    }
}
