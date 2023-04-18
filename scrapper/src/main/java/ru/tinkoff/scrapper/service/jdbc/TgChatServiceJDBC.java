package ru.tinkoff.scrapper.service.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tinkoff.scrapper.enyity.ChatEntity;
import ru.tinkoff.scrapper.repository.TgChatRepository;
import ru.tinkoff.scrapper.service.interfaces.TgChatService;

import java.util.List;

@Service
public class TgChatServiceJDBC implements TgChatService {

    @Autowired
    TgChatRepository repository;

    @Override
    public Integer add(Long chatId) {
        return repository.add(chatId);
    }

    @Override
    public List<ChatEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public Integer removeByChatId(Long chatId) {
        return repository.removeByChatId(chatId);
    }

    @Override
    public Integer removeById(Long tgChatId) {
        return repository.removeById(tgChatId);
    }

    @Override
    public Integer updateById(Long id, Long tgChatId) {
        return repository.updateById(id, tgChatId);
    }
}
