package ru.tinkoff.bot.tg;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.tinkoff.bot.configuration.ApplicationConfig;


@Component
public class LinkTelegramBot extends TelegramBot {

    private final MsgHandler msgHandler;



    @Autowired
    public LinkTelegramBot(ApplicationConfig config,
                           MsgHandler msgHandler) {
        super(config.token());
        this.msgHandler = msgHandler;
        run();
    }

    public void run() {
        this.setUpdatesListener(updates -> {
            updates.forEach(this::process);
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }

    private void process(Update update) {
        Message message = update.message();
        if (message != null && message.text() != null) {
            SendMessage response = msgHandler.serveCommand(message);
            this.execute(response);
        }
    }

    public void sendCommand(Long id, String msg) {
        this.execute(new SendMessage(id, msg));
    }



}