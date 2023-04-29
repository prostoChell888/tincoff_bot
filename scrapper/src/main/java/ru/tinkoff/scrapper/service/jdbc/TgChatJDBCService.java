package ru.tinkoff.scrapper.service.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tincoff.exeptios.BadRequestException;
import ru.tincoff.exeptios.types.NotFoundException;
import ru.tinkoff.scrapper.enyity.ChatEntity;
import ru.tinkoff.scrapper.repository.JDBC.LinkJDBCRepository;
import ru.tinkoff.scrapper.repository.JDBC.TgChatJDBCRepository;
import ru.tinkoff.scrapper.repository.LinkRepository;
import ru.tinkoff.scrapper.repository.TgChatRepository;
import ru.tinkoff.scrapper.service.TgChatService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TgChatJDBCService implements TgChatService {


    private final TgChatJDBCRepository chatRepository;


    private final LinkJDBCRepository linkJDBCRepository;

    @Override
    public void register(Long tgChatId) {
        if (chatRepository.findByTgChatId(tgChatId).size() !=  0) {
            throw new BadRequestException("Чат с id" + tgChatId + "уже добавлен");
        }

         chatRepository.add(tgChatId);
    }

    @Override
    public List<ChatEntity> findAll() {
        return chatRepository.findAll();
    }

    @Override
    public List<Long> findChatByLinkId(Long linkId) {
        if (linkJDBCRepository.findLinkById(linkId).size() !=  0) {
            throw new BadRequestException("Чат с id" + linkId + "не найден");
        }

        return chatRepository.findChatByLinkId(linkId);
    }

    @Override
    public Long removeById(Long id) {
        var tgChatOptional = chatRepository.findById(id);
        if (tgChatOptional.size() == 0) {
            throw new NotFoundException("Чат с id" + id + "не найден");
        }

        return chatRepository.removeByTgChatId(id);
    }

    @Override
    public Long unregister(Long tgChatId) {
        var tgChatOptional = chatRepository.findByTgChatId(tgChatId);
        if (tgChatOptional.size() == 0) {
            throw new NotFoundException("Чат с id" + tgChatId + "не найден");
        }

        return chatRepository.removeById(tgChatId);
    }

    @Override
    public Long updateById(Long id, Long tgChatId) {
        var tgChatOptional = chatRepository.findById(id);
        if (tgChatOptional.size() == 0) {
            throw new NotFoundException("Чат с id" + id + "не найден");
        }

        return chatRepository.updateById(id, tgChatId);
    }
}
