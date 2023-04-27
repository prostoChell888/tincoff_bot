package ru.tinkoff.scrapper.enyity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatEntity {

    private Long id;
    private Long tgChatId;
}
