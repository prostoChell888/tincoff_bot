package ru.tinkoff.bot.tg.states;


import org.jetbrains.annotations.NotNull;
import ru.tinkoff.bot.tg.User;

public sealed interface State permits AddLinkState, DefaultState, DelLinkState {
    default String untrackCommand(User user) {
        user.setState(TypeOfState.DELL_LINK);
        return "Введите ссылку которую хотите прегратить отслеживания";

    }

    @NotNull
    default String trackCommand(User user) {

        user.setState(TypeOfState.ADD_LINK);
        return "Введите ссылку для отслеживания";

    }

    @NotNull
    default String listCommand(User user) {
        if (user.getLinkSet().size() == 0){
            return "Отслеживаемые ссылки отсутсвуют";
        }

        var sb = new StringBuilder("Отслеживаемые ссылки:\n");

        for (var link : user.getLinkSet()) {
            sb.append(link.toString()).append('\n');
        }
        return sb.toString();
    }

    @NotNull
    default String helpCommand(User user) {
        return """
                /start -- зарегистрировать пользователя
                /help -- вывести окно с командами
                /track -- начать отслеживание ссылки
                /untrack -- прекратить отслеживание ссылки
                /list -- показать список отслеживаемых ссылок""";
    }

    @NotNull
    default String startCommand(User user) {
        return "Вы зарегистриованы";
    }


    String notCommand(User user, String msg);
}
