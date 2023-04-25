package ru.tinkoff.scrapper.repository.JPA;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tinkoff.scrapper.enyity.ChatEntity;

@Repository
public interface TgChatJPARepository extends JpaRepository<ChatEntity, Long> {

    ChatEntity findByTgChatId(Long tgChatId);
}
