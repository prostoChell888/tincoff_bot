package ru.tinkoff.scrapper.repository.JPA;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tinkoff.scrapper.enyity.JPA.JPAChatEntity;
import ru.tinkoff.scrapper.enyity.JPA.JPALinkEntity;
import ru.tinkoff.scrapper.enyity.LinkEntity;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Repository
public interface LinkJPARepository extends JpaRepository<JPALinkEntity, Long> {


    List<JPALinkEntity> findAllByChats(JPAChatEntity link);

    Optional<JPALinkEntity> findByLink(URI link );
}
