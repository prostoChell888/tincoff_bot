package ru.tinkoff.scrapper.enyity.JPA;


import jakarta.persistence.*;
import lombok.*;

import java.net.URI;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "link")
public class JPALinkEntity {

    @Id
    @Column(name = "link_id")
    private Long linkId;

    @Column(name = "link")
    private URI link;

    @Column(name = "last_update")
    private Timestamp lastUpdate;

    @ManyToMany
    @JoinTable(name = "chat_link",
            joinColumns = @JoinColumn(name = "link_id"),
            inverseJoinColumns = @JoinColumn(name = "chat_id"))
    private List<JPAChatEntity> chats = new ArrayList<>();


}
