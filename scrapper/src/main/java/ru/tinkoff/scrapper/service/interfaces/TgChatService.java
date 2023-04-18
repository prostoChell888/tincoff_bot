package ru.tinkoff.scrapper.service.interfaces;

import ru.tinkoff.scrapper.enyity.ChatEntity;

import java.util.List;

public interface TgChatService {

     Integer add(Long chatId);

    List<ChatEntity> findAll();

    Integer removeByChatId(Long chatId);

    Integer removeById(Long tgChatId);

    Integer updateById(Long id, Long tgChatId);

}
