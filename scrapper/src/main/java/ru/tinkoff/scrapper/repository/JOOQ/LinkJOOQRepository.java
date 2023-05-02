package ru.tinkoff.scrapper.repository.JOOQ;

import lombok.RequiredArgsConstructor;
import org.jooq.Batch;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;
import ru.tinkoff.scrapper.enyity.LinkEntity;
import ru.tinkoff.scrapper.repository.LinkRepository;
import static scrapper.domains.jooq.tables.Link.LINK;
import static scrapper.domains.jooq.tables.Chat.CHAT;
import static scrapper.domains.jooq.tables.ChatLink.CHAT_LINK;
import scrapper.domains.jooq.tables.records.LinkRecord;


import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;



@Repository
@RequiredArgsConstructor
public class LinkJOOQRepository implements LinkRepository {
    private final DSLContext create;

    @Override
    public List<LinkEntity> findAll() {
        var res = create.select().from(LINK)
                .fetch()
                .into(LinkRecord.class);
        return res.stream()
                .map(this::fromTableToEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<LinkEntity> findLinkById(Long id) {
        var res = create.select().from(LINK)
                .where(LINK.LINK_ID.eq(id))
                .fetch()
                .into(LinkRecord.class);
        return res.stream()
                .map(this::fromTableToEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<LinkEntity> findAllOrderByDate() {

        var res = create
                .selectFrom(LINK)
                .orderBy(LINK.LAST_UPDATE)
                .fetch();

        return res.stream().map(this::fromTableToEntity).collect(Collectors.toList());
    }

    @Override
    public List<LinkEntity> findByURI(String link) {
        var res = create.selectFrom(LINK).where(LINK.URL.eq(link)).fetch();
        return res.stream().map(this::fromTableToEntity).collect(Collectors.toList());
    }

    @Override
    public Long add(String link) {
        var res = create.insertInto(LINK, LINK.URL, LINK.LAST_UPDATE).values(link, LocalDateTime.now())
                .returning(LINK.LINK_ID).fetch().into(Long.class);
        return res.size() > 0 ? res.get(0) : null;
    }

    @Override
    public List<LinkEntity> findTrackedLinks(Long chatId) {
        var res = create.select(LINK.fields())
                .from(LINK)
                .join(CHAT)
                .on(LINK.LINK_ID.eq(CHAT_LINK.LINK_ID))
                .where(CHAT.CHAT_ID.eq(chatId))
                .fetch().into(LINK);

        return res.stream().map(this::fromTableToEntity).collect(Collectors.toList());
    }

    @Override
    public void addLinkToChat(Long chatId, Long linkId) {
        create.insertInto(CHAT_LINK, CHAT_LINK.CHAT_ID, CHAT_LINK.LINK_ID)
                .values(chatId, linkId)
                .execute();
    }

    @Override
    public boolean isRecordExistsByLinkIdAndTgChatId(Long linkId, Long chatId) {
        var count = create.selectCount()
                .from(CHAT_LINK)
                .where(CHAT_LINK.CHAT_ID.eq(chatId))
                .and(CHAT_LINK.LINK_ID.eq(linkId)).fetch().into(Integer.class);

        return count.get(0) > 0;
    }

    @Override
    public void updateLinksDateTimeToNow(List<LinkEntity> links) {
        List<Long> linksId = new ArrayList<>();
        for (var link:links) {
            if (link!= null) linksId.add(link.getLinkId());
        }

        Batch batch = create.batch(
                DSL.update(LINK)
                        .set(LINK.LAST_UPDATE, LocalDateTime.now())
                        .where(DSL.field("id").in(linksId))
        );

        batch.execute();
    }


    private LinkEntity fromTableToEntity(LinkRecord l) {
        l.getLastUpdate();
        try {
            return new LinkEntity(
                    l.getLinkId(),
                    new URI(l.getUrl()),
                    Timestamp.valueOf(l.getLastUpdate()));
        } catch (URISyntaxException e) {
            throw new RuntimeException("not valid URI from BD");
        }
    }
}
