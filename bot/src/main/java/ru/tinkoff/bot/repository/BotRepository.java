package ru.tinkoff.bot.repository;

import org.springframework.stereotype.Component;
import ru.tinkoff.bot.tg.User;

import java.util.HashMap;


@Component
public class BotRepository {
    HashMap<Long, User> chatStates = new HashMap<>();


    public User getUserById(Long id) {
        return chatStates.get(id);
    }

    public User addUserById(Long id, User user){
        return chatStates.put(id, user);
    }




}
