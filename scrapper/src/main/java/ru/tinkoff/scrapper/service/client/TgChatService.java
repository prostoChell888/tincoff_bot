package ru.tinkoff.scrapper.service.client;


import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.tincoff.exeptios.BadRequestException;
import ru.tinkoff.scrapper.util.validation.anotations.Id;

@Service
@Validated
public class TgChatService {
    public void addChat(@Id Long id) {
        if (id.intValue() % 2 == 0) throw new BadRequestException("четный id");


    }

    public void delChat(@Id Long id) {
        if (id.intValue() % 2 == 0) throw new BadRequestException("четный id");


    }
}
