package org.example.feature.telegram;

import org.example.feature.telegram.BotConstants;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

public class CurrencyTelegramBot extends TelegramLongPollingBot {
    @Override
    public void onUpdateReceived(Update update) {
        System.out.println("Update received");
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
