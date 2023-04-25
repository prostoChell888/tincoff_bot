package ru.tinkoff.scrapper.repository.JOOQ;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.tinkoff.scrapper.enyity.LinkEntity;
import ru.tinkoff.scrapper.repository.LinkRepository;
import scrapper.domains.jooq.tables.Link;
import scrapper.domains.jooq.tables.Chat;
import scrapper.domains.jooq.tables.ChatLink;
import scrapper.domains.jooq.tables.records.LinkRecord;


import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.jooq.impl.DSL.count;

@Repository
@RequiredArgsConstructor
public class LinkJOOQRepository implements LinkRepository {


    final DSLContext create;

    final Link ls = Link.LINK;
    final Chat chat = Chat.CHAT;

    final ChatLink chatLink = ChatLink.CHAT_LINK;

    @Override
    public List<LinkEntity> findAll() {
        var res = create.selectFrom(ls)
                .fetch()
                .into(LinkRecord.class);
        return res.stream()
                .map(this::fromTableToEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<LinkEntity> findLinkById(Long id) {
        var res = create.selectFrom(ls)
                .where(ls.LINK_ID.eq(id))
                .fetch();
        return res.stream()
                .map(this::fromTableToEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<LinkEntity> findAllOrderByDate() {
        var res = create
                .selectFrom(ls)
                .orderBy(ls.LAST_UPDATE.desc())
                .fetch();
        return res.stream().map(this::fromTableToEntity).collect(Collectors.toList());
    }

    @Override
    public List<LinkEntity> findByURI(String link) {
        var res = create.selectFrom(ls).where(ls.LINK_.eq(link)).fetch();
        return res.stream().map(this::fromTableToEntity).collect(Collectors.toList());
    }

    @Override
    public Long add(String link) {
        var res = create.insertInto(ls, ls.LINK_, ls.LAST_UPDATE).values(link, LocalDateTime.now())
                .returning(ls.LINK_ID).fetch().into(Long.class);
        return res.size() > 0 ? res.get(0) : null;
    }

    @Override
    public List<LinkEntity> findTrackedLinks(Long chatId) {
        var res = create.select(ls.fields())
                .from(ls)
                .join(chat)
                .on(ls.LINK_ID.eq(chatLink.LINK_ID))
                .where(chat.CHAT_ID.eq(chatId))
                .fetch().into(ls);

        return res.stream().map(this::fromTableToEntity).collect(Collectors.toList());
    }

    @Override
    public void addLinkToChat(Long chatId, Long linkId) {
        create.insertInto(chatLink, chatLink.CHAT_ID, chatLink.LINK_ID)
                .values(chatId, linkId)
                .execute();
    }

    @Override
    public boolean isRecordExistsByLinkIdAndTgChatId(Long linkId, Long chatId) {
        var count = create.select(chatLink, count())
                .from(chatLink)
                .where(chatLink.CHAT_ID.eq(chatId))
                .and(chatLink.LINK_ID.eq(linkId)).fetch().into(Integer.class);

        return count.get(0) > 0;
    }

    private LinkEntity fromTableToEntity(LinkRecord l) {
        l.getLastUpdate();
        try {
            return new LinkEntity(
                    l.getLinkId(),
                    new URI(l.getLink()),
                    Timestamp.valueOf(l.getLastUpdate()));
        } catch (URISyntaxException e) {
            throw new RuntimeException("not valid URI from BD");
        }
    }
}
