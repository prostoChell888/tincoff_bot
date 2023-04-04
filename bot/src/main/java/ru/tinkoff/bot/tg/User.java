package ru.tinkoff.bot.tg;

import lombok.Getter;
import ru.tinkoff.bot.tg.states.TypeOfState;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Getter
public class User {
    private final Long id;
    private final List<URL> linkSet = new ArrayList<>();
    private TypeOfState state = TypeOfState.DEFAULT;

    public User(Long id) {
        this.id = id;
    }

    public void setState(TypeOfState state) {
        this.state = state;
    }

    public boolean addLink(URL link) {
        return linkSet.add(link);
    }

    public boolean delLink(URL link) {
        return linkSet.remove(link);
    }

}

