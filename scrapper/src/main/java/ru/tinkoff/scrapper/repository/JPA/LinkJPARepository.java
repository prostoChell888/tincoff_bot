package ru.tinkoff.scrapper.repository.JPA;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tinkoff.scrapper.enyity.LinkEntity;

@Repository
public interface LinkJPARepository extends JpaRepository<LinkEntity, Long> {

}
