package org.example.feature.command;


import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Arrays;
import java.util.Collections;


public class StartCommand extends BotCommand {
    public StartCommand() {
        super("start", "Start command");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {

        String startText = "Ласкаво просимо! Цей бот допоможе відслідковувати актуальні курси валют";

        SendMessage message = new SendMessage();
        message.setText(startText);
        message.setChatId(Long.toString(chat.getId()));

       
        InlineKeyboardButton.InlineKeyboardButtonBuilder infoButton = InlineKeyboardButton
                .builder()
                .text("Отримати інфо")
                .callbackData("Отримати інфо");

       
        InlineKeyboardButton.InlineKeyboardButtonBuilder settingsButton = InlineKeyboardButton
                .builder()
                .text("Налаштування")
                .callbackData("Налаштування");

      
        InlineKeyboardMarkup keyboard = InlineKeyboardMarkup
                .builder()
                .keyboard(Collections.singletonList(
                        Arrays.asList(infoButton.build(), settingsButton.build())
                ))
                .build();

        message.setReplyMarkup(keyboard);

        try {
            absSender.execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
