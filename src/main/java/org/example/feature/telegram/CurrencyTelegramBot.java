package org.example.feature.telegram;


import org.example.feature.command.StartCommand;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Arrays;
import java.util.Collections;
import java.util.Currency;
import java.util.List;

public class CurrencyTelegramBot extends TelegramLongPollingCommandBot {

    public List<Currency> currencies;
    public CurrencyTelegramBot(){
        register(new StartCommand());
    }
    @Override
    public void processNonCommandUpdate(Update update) {
        if(update.hasCallbackQuery()){
            if(update.getCallbackQuery().getData().equals("Налаштування")){
                createSettingsButton(update);
            }
            if(update.getCallbackQuery().getData().equals("Банк")) {
                createBankButton(update);
            }
            if(update.getCallbackQuery().getData().equals("Валюти")) {
                createCurrencyButton(update);
            }
        }
    }

    private void createSettingsButton(Update update) {
        String startText = "Налаштування";

        SendMessage message = new SendMessage();
        message.setText(startText);
        message.setChatId(update.getCallbackQuery().getFrom().getId());

        InlineKeyboardButton.InlineKeyboardButtonBuilder banksButton = InlineKeyboardButton
                .builder()
                .text("Банк")
                .callbackData("Банк");

        InlineKeyboardButton.InlineKeyboardButtonBuilder currencyButton = InlineKeyboardButton
                .builder()
                .text("Валюти")
                .callbackData("Валюти");


        InlineKeyboardMarkup keyboard = InlineKeyboardMarkup
                .builder()
                .keyboard(Collections.singletonList(
                        Arrays.asList(banksButton.build(), currencyButton.build())
                ))
                .build();

        message.setReplyMarkup(keyboard);

        sendApiMethodAsync(message);
    }

    private void createBankButton(Update update) {
        String startText = "Оберіть банк з якого ви хочете отримувати актуальний курс валют:";

        SendMessage message = new SendMessage();
        message.setText(startText);
        message.setChatId(update.getCallbackQuery().getFrom().getId());

        InlineKeyboardButton.InlineKeyboardButtonBuilder nbuButton = InlineKeyboardButton
                .builder()
                .text("НБУ")
                .callbackData("НБУ");

        InlineKeyboardButton.InlineKeyboardButtonBuilder privatButton = InlineKeyboardButton
                .builder()
                .text("ПриватБанк")
                .callbackData("ПриватБанк");

        InlineKeyboardButton.InlineKeyboardButtonBuilder monoButton = InlineKeyboardButton
                .builder()
                .text("МоноБанк")
                .callbackData("Монобанк");


        InlineKeyboardMarkup keyboard = InlineKeyboardMarkup
                .builder()
                .keyboard(Collections.singletonList(
                        Arrays.asList(nbuButton.build(), privatButton.build(), monoButton.build())
                ))
                .build();

        message.setReplyMarkup(keyboard);

        sendApiMethodAsync(message);
    }

    private void createCurrencyButton(Update update) {
        String startText = "Оберіть валюти, курс яких ви хочете отримувати:";

        SendMessage message = new SendMessage();
        message.setText(startText);
        message.setChatId(update.getCallbackQuery().getFrom().getId());

        InlineKeyboardButton.InlineKeyboardButtonBuilder usdButton = InlineKeyboardButton
                .builder()
                .text("✓USD")
                .callbackData("USD");

        InlineKeyboardButton.InlineKeyboardButtonBuilder eurButton = InlineKeyboardButton
                .builder()
                .text("EUR")
                .callbackData("EUR");


        InlineKeyboardMarkup keyboard = InlineKeyboardMarkup
                .builder()
                .keyboard(Collections.singletonList(
                        Arrays.asList(usdButton.build(), eurButton.build())
                ))
                .build();

        message.setReplyMarkup(keyboard);

        sendApiMethodAsync(message);
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
