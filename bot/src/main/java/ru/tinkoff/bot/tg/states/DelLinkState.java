package ru.tinkoff.bot.tg.states;

import org.jetbrains.annotations.NotNull;
import ru.tinkoff.bot.tg.User;

import java.net.MalformedURLException;
import java.net.URL;

public final class DelLinkState implements State {

    @Override
    public String untrackCommand(User user) {
        return State.super.untrackCommand(user);
    }

    @Override
    public @NotNull String trackCommand(User user) {
        user.setState(TypeOfState.ADD_LINK);
        return State.super.trackCommand(user);
    }

    @Override
    public @NotNull String listCommand(User user) {
        user.setState(TypeOfState.DEFAULT);
        return State.super.listCommand(user);
    }

    @Override
    public @NotNull String helpCommand(User user) {
        user.setState(TypeOfState.DEFAULT);
        return State.super.helpCommand(user);
    }

    @Override
    public @NotNull String startCommand(User user) {
        user.setState(TypeOfState.DEFAULT);
        return State.super.startCommand(user);
    }

    @Override
    public String notCommand(User user, String msg) {
        URL uri;
        try {
            uri = new URL(msg);
        } catch (MalformedURLException e) {
            return   "переданный URL не валидный";
        }

        user.setState(TypeOfState.DEFAULT);
        if (!user.delLink(uri)) {
            return "Ссылка не найдена в отслеживаемых";
        } else {
            return "Ссылка уделена из отслеживаемых";

        }
    }
}
