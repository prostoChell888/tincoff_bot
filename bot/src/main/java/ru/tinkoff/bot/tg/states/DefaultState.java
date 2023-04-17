package ru.tinkoff.bot.tg.states;

import org.jetbrains.annotations.NotNull;
import ru.tinkoff.bot.tg.User;

public final class DefaultState implements State {

    @Override
    public String untrackCommand(User user) {
        user.setState(TypeOfState.DELL_LINK);
        return State.super.untrackCommand(user);
    }

    @Override
    public @NotNull String trackCommand(User user) {
        user.setState(TypeOfState.ADD_LINK);
        return State.super.trackCommand(user);
    }

    @Override
    public @NotNull String listCommand(User user) {
        return State.super.listCommand(user);
    }

    @Override
    public @NotNull String helpCommand(User user) {
        return State.super.helpCommand(user);
    }

    @Override
    public @NotNull String startCommand(User user) {
        return State.super.startCommand(user);
    }

    @Override
    public String notCommand(User user, String msg) {
        return "Команда не распознана";
    }
}
