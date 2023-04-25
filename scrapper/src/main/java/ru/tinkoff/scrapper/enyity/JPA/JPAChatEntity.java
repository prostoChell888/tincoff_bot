package ru.tinkoff.scrapper.enyity.JPA;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "chat")
public class JPAChatEntity {

    @Id
    @Column(name = "chat_id")
    private Long chatId;

    @Column(name = "tg_chat_id")
    private Long tgChatId;

    @ManyToMany(mappedBy = "chats")
    private List<JPALinkEntity> links = new ArrayList<>();
}
