package ru.tinkoff.scrapper.repository.JPA;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.tinkoff.scrapper.enyity.ChatEntity;
import ru.tinkoff.scrapper.enyity.JPA.JPAChatEntity;
import ru.tinkoff.scrapper.enyity.JPA.JPALinkEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface TgChatJPARepository extends JpaRepository<JPAChatEntity, Long> {
    Optional<JPAChatEntity> findByTgChatId(Long tgChatId);

    Long deleteAllByTgChatId(Long tgChatId);

    @Query(
            "SELECT chat.tgChatId " +
                    "FROM JPAChatEntity chat " +
                    "JOIN chat.links links " +
                    "WHERE :linkId = links.linkId"
    )
    List<Long> getByLinksContaining(@Param("linkId")Long linkId);
}
