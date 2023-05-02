package ru.tinkoff.scrapper.repository;

import ru.tinkoff.scrapper.enyity.ChatEntity;

import java.util.List;

public interface TgChatRepository {
    void add(Long tgChatId);

    List<ChatEntity> findAll();

    List<ChatEntity> findByTgChatId(Long tgChatId);

    List<ChatEntity> findById(Long id);

    Long removeByTgChatId(Long tgChatId);

    Long removeById(Long chatId);

    Long updateById(Long id, Long tgChatId);

    void utracedLinks(Long chatId);

    List<Long> findChatByLinkId(Long linkId);

    List<Long> findIdByTgChatId(Long tgChatId);
}
