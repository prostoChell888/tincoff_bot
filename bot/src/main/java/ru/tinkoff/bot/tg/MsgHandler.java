package ru.tinkoff.bot.tg;


import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;
import ru.tinkoff.bot.tg.states.*;
import ru.tinkoff.bot.repository.BotRepository;

import java.util.Map;

@Component
public class MsgHandler {
    private final BotRepository botRepository;
    private State state;

    public MsgHandler(BotRepository botRepository) {
        this.botRepository = botRepository;
    }

    private final static Map<TypeOfState, State> states = Map.of(
            TypeOfState.DEFAULT, new DefaultState(),
            TypeOfState.ADD_LINK, new AddLinkState(),
            TypeOfState.DELL_LINK, new DelLinkState()
    );

    public SendMessage serveCommand(Message massage) {
        Long id = massage.chat().id();
        User user = botRepository.getUserById(id);

        if (user == null) {
            user = new User(id);
            botRepository.addUserById(id, user);
        }
        String msg = serveMsg(user, massage.text());

        return new SendMessage(id, msg);
    }

    private String serveMsg(User user, String message){
        state = states.get(user.getState());

        return switch (message) {
            case "/start" ->  state.startCommand(user);
            case "/help" -> state.helpCommand(user);
            case "/list" ->  state.listCommand(user);
            case "/track" ->  state.trackCommand(user);
            case "/untrack" ->  state.untrackCommand(user);
            default ->  state.notCommand(user, message);
        };
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
