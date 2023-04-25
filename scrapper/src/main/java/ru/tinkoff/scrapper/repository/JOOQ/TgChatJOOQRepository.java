package ru.tinkoff.scrapper.repository.JOOQ;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.tinkoff.scrapper.enyity.ChatEntity;
import ru.tinkoff.scrapper.repository.TgChatRepository;
import scrapper.domains.jooq.tables.Chat;
import scrapper.domains.jooq.tables.ChatLink;
import scrapper.domains.jooq.tables.Link;
import scrapper.domains.jooq.tables.records.ChatRecord;

import java.util.List;
import java.util.stream.Collectors;


@Repository
@RequiredArgsConstructor
public class TgChatJOOQRepository implements TgChatRepository {

    final DSLContext create;

    final Link link = scrapper.domains.jooq.tables.Link.LINK;
    final Chat chat = scrapper.domains.jooq.tables.Chat.CHAT;

    final ChatLink chatLink = scrapper.domains.jooq.tables.ChatLink.CHAT_LINK;

    @Override
    public void add(Long tgChatId) {
        create.insertInto(chat, chat.TG_CHAT_ID).values(tgChatId).execute();
    }

    @Override
    public List<ChatEntity> findAll() {
        var res = create.selectFrom(chat).fetch().into(ChatRecord.class);
        return res.stream().map(this::fromTableToEntity).collect(Collectors.toList());
    }

    @Override
    public List<ChatEntity> findByTgChatId(Long tgChatId) {
        var res = create.selectFrom(chat).where(chat.TG_CHAT_ID.eq(tgChatId)).fetch();
        return res.stream().map(this::fromTableToEntity).collect(Collectors.toList());
    }

    @Override
    public List<ChatEntity> findById(Long id) {
        var res = create.selectFrom(chat).where(chat.CHAT_ID.eq(id)).fetch();
        return res.stream().map(this::fromTableToEntity).collect(Collectors.toList());
    }

    @Override
    public Long removeByTgChatId(Long tgChatId) {
        var res = create.delete(chat)
                .where(chat.TG_CHAT_ID.eq(tgChatId))
                .returningResult(chat.CHAT_ID)
                .fetch()
                .into(Long.class);

        return (res.size() > 0) ? res.get(0) : null;
    }

    @Override
    public Long removeById(Long chatId) {
        var res = create.delete(chat)
                .where(chat.CHAT_ID.eq(chatId))
                .returningResult(chat.CHAT_ID)
                .fetch()
                .into(Long.class);

        return (res.size() > 0) ? res.get(0) : null;
    }

    @Override
    public Long updateById(Long id, Long tgChatId) {
        var res = create.update(chat)
                .set(chat.TG_CHAT_ID, tgChatId)
                .where(chat.CHAT_ID.eq(id))
                .returningResult(chat.CHAT_ID)
                .fetch()
                .into(Long.class);

        return (res.size() > 0) ? res.get(0) : null;
    }

    @Override
    public void utracedLinks(Long chatId) {
        create.delete(chatLink)
                .where(chatLink.CHAT_ID.eq(chatId))
                .execute();
    }

    @Override
    public List<Long> findChatByLinkId(Long linkId) {
        return create.select(chat.TG_CHAT_ID)
                .from(chat)
                .join(chatLink)
                .on(chatLink.CHAT_ID.eq(chat.CHAT_ID))
                .where(chatLink.LINK_ID.eq(linkId))
                .fetch().into(Long.class);
    }

    @Override
    public List<Long> findIdByTgChatId(Long tgChatId) {
         return  create
                 .selectFrom(chat)
                 .where(chat.TG_CHAT_ID.eq(tgChatId))
                 .fetchInto(Long.class);
    }

    private ChatEntity fromTableToEntity(ChatRecord chat) {
        return new ChatEntity(chat.getChatId(), chat.getTgChatId());
    }

}
