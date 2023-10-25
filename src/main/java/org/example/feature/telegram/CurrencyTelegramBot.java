package org.example.feature.telegram;


import org.example.feature.command.StartCommand;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class CurrencyTelegramBot extends TelegramLongPollingCommandBot {

    public CurrencyTelegramBot(){
        register(new StartCommand());
    }
    @Override
    public void processNonCommandUpdate(Update update) {
        System.out.println("Non-command here!");

    }

    @Override
    public String  getBotToken() {
        return BotConstants.BOT_TOKEN;
    }

    @Override
    public String getBotUsername() {
        return BotConstants.BOT_NAME;
    }


}
