package ru.tinkoff.scrapper.service;

import ru.tinkoff.scrapper.enyity.ChatEntity;

import java.util.List;

public interface TgChatService {

    void register(Long tgChatId);

    Long unregister(Long tgChatId);

    List<ChatEntity> findAll();

    List<Long> findChatByLinkId(Long linkid);


    Long removeById(Long chatId);

    Long updateById(Long id, Long tgChatId);

}
