package ru.tinkoff.bot.tg;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.tinkoff.bot.repository.BotRepository;
import ru.tinkoff.bot.tg.states.TypeOfState;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class MsgHandlerTest {


 

    @Mock
    User user;
    @Mock
    BotRepository botRepository;
    @Mock
    Message message;

    @Mock
    Chat chat;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void serveMsg_getTrackLinkList_userInDefaultState() throws MalformedURLException {
        when(user.getLinkSet()).thenReturn(List.of(
                new URL("https://habr.com"),
                new URL("https://mvnrepository.com"),
                new URL("https://mail.google.com")));
        when(user.getState()).thenReturn(TypeOfState.DEFAULT);
        when(botRepository.getUserById(1L)).thenReturn(user);
        MsgHandler msgHandler = new MsgHandler(botRepository);
        when(message.text()).thenReturn("/list");
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(1L);

        SendMessage sendMessage = msgHandler.serveCommand(message);

        String msgResponse = """
                Отслеживаемые ссылки:
                https://habr.com
                https://mvnrepository.com
                https://mail.google.com
                """;
        var expectedMessage = new SendMessage(1L, msgResponse);
        assertEquals(sendMessage.getParameters().get("text"),
                expectedMessage.getParameters().get("text"));
    }

}
