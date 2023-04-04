package ru.tinkoff.bot.service;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.tinkoff.bot.dto.request.LinkUpdateRequest;

@Validated
@Service
public class BotService {

    public void update(@Valid LinkUpdateRequest request) {
         if (request.getId() % 2 == 0) throw new ArithmeticException("четный id");
    }
}
