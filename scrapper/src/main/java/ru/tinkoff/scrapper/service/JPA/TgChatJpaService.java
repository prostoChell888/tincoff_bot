package ru.tinkoff.scrapper.service.JPA;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tincoff.exeptios.BadRequestException;
import ru.tincoff.exeptios.NotFoundException;
import ru.tinkoff.scrapper.enyity.ChatEntity;
import ru.tinkoff.scrapper.enyity.JPA.JPAChatEntity;
import ru.tinkoff.scrapper.repository.JOOQ.LinkJOOQRepository;
import ru.tinkoff.scrapper.repository.JOOQ.TgChatJOOQRepository;
import ru.tinkoff.scrapper.repository.JPA.LinkJPARepository;
import ru.tinkoff.scrapper.repository.JPA.TgChatJPARepository;
import ru.tinkoff.scrapper.service.TgChatService;

import java.util.List;
import java.util.stream.Collectors;



@RequiredArgsConstructor
public class TgChatJpaService implements TgChatService {

    private final TgChatJPARepository chatRepository;

    @Override
    public void register(Long tgChatId) {
        if (chatRepository.findByTgChatId(tgChatId).isPresent() ){
            throw new BadRequestException("Чат с id" + tgChatId + "уже добавлен");
        }

        var newChat = new JPAChatEntity();
        newChat.setTgChatId(tgChatId);
        chatRepository.save(newChat);
    }

    @Override
    public Long unregister(Long tgChatId) {
        if (chatRepository.findByTgChatId(tgChatId).isEmpty() ){
            throw new NotFoundException("Чат с id" + tgChatId + " не найден");
        }

        return chatRepository.deleteAllByTgChatId(tgChatId);
    }

    @Override
    public List<ChatEntity> findAll() {
        return chatRepository.findAll()
                .stream()
                .map(this::JpaToEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<Long> findChatByLinkId(Long linkid) {
        var chatOptional = chatRepository.findById(linkid);
        if (chatOptional.isEmpty()){
            throw new NotFoundException("Чат с id" + linkid + " не найден");
        }
        return chatRepository.getByLinksContaining(linkid);
    }

    @Override
    public Long removeById(Long chatId) {
        var chat = chatRepository.findById(chatId);
        if (chat.isEmpty() ){
            throw new NotFoundException("Чат с id" + chatId + " не найден");
        }

        chatRepository.deleteById(chatId);

        return chat.get().getTgChatId();
    }

    @Override
    public Long updateById(Long id, Long tgChatId) {
        var chatOptional = chatRepository.findById(id);
        if (chatOptional.isEmpty() ){
            throw new NotFoundException("Чат с id" + id + " не найден");
        }
        var chat = chatOptional.get();
        chat.setTgChatId(tgChatId);
        return tgChatId;
    }

    ChatEntity JpaToEntity(JPAChatEntity entity) {
        return new ChatEntity(entity.getChatId(),
                entity.getTgChatId());
    }
}
