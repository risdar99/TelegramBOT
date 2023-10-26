package org.example.feature.telegram;


import lombok.Data;
import org.example.feature.command.StartCommand;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.*;
     @Data
public class CurrencyTelegramBot extends TelegramLongPollingCommandBot {

    private float roundedRate;
    private float twoCharRate;
    private float threeCharRate;
    private float fourCharRate;

    public List<Currency> currencies;
    public CurrencyTelegramBot(){
        register(new StartCommand());
    }
    @Override
    public void processNonCommandUpdate(Update update) {
        roundedRate = twoCharRate;

        if(update.hasCallbackQuery()){
            if(update.getCallbackQuery().getData().equals("Налаштування")) {
                createSettingsButton(update);
            }
            if(update.getCallbackQuery().getData().equals("Кількість знаків після коми")) {
                roundingButton(update);
            }
            if(update.getCallbackQuery().getData().equals("Час оповіщень")) {
                notificationButton(update);
            }
        }
    }

    private void createSettingsButton(Update update) {
        String startText = "Налаштування";

        SendMessage message = new SendMessage();
        message.setText(startText);
        message.setChatId(update.getCallbackQuery().getFrom().getId());


        InlineKeyboardButton.InlineKeyboardButtonBuilder roundingButton = InlineKeyboardButton
                .builder()
                .text("Кількість знаків після коми")
                .callbackData("Кількість знаків після коми");

        InlineKeyboardButton.InlineKeyboardButtonBuilder notificationButton = InlineKeyboardButton
                .builder()
                .text("Час оповіщень")
                .callbackData("Час оповіщень");

        InlineKeyboardMarkup keyboard = InlineKeyboardMarkup
                .builder()
                .keyboard(Collections.singletonList(
                        Arrays.asList( roundingButton.build(),notificationButton.build())
                ))
                .build();

        message.setReplyMarkup(keyboard);

        sendApiMethodAsync(message);
    }
    private void roundingButton(Update update){
        String startText = "Виберіть кількість знаків після коми";
        SendMessage message = new SendMessage();
        message.setText(startText);
        message.setChatId(update.getCallbackQuery().getFrom().getId());

        InlineKeyboardButton.InlineKeyboardButtonBuilder twoButton = InlineKeyboardButton
                .builder()
                .text("2")
                .callbackData("2char");

        InlineKeyboardButton.InlineKeyboardButtonBuilder threeButton = InlineKeyboardButton
                .builder()
                .text("3")
                .callbackData("3char");


        InlineKeyboardButton.InlineKeyboardButtonBuilder fourButton = InlineKeyboardButton
                .builder()
                .text("4")
                .callbackData("4char");

        InlineKeyboardMarkup keyboard = InlineKeyboardMarkup
                .builder()
                .keyboard(Collections.singletonList(
                        Arrays.asList(twoButton.build(), threeButton.build(),fourButton.build())
                ))
                .build();

        message.setReplyMarkup(keyboard);

        sendApiMethodAsync(message);
    }

    private void notificationButton(Update update){
        String startText = "Виберіть час оповіщень";
        SendMessage message = new SendMessage();
        message.setText(startText);
        message.setChatId(update.getCallbackQuery().getFrom().getId());

        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
        keyboard.setResizeKeyboard(true);
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();

        row.add("9");
        row.add("10");
        row.add("11");
        row.add("12");
        row.add("13");
        keyboardRows.add(row);

        row = new KeyboardRow();
        row.add("14");
        row.add("15");
        row.add("16");
        row.add("17");
        row.add("18");
        keyboardRows.add(row);
        keyboard.setKeyboard(keyboardRows);
        message.setReplyMarkup(keyboard);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    private void roundingRate(double rate,Update update) {


        if (update.hasCallbackQuery()) {

            if ("2char".equals(update.getCallbackQuery().getData())) {
                twoCharRate = Math.round(rate * 100d) / 100.f;
                roundedRate = twoCharRate;
            } else if ("3char".equals(update.getCallbackQuery().getData())) {
                threeCharRate = Math.round(rate * 1000d) / 1000.f;
                roundedRate = threeCharRate;
            } else if ("4char".equals(update.getCallbackQuery().getData())) {
                fourCharRate = Math.round(rate * 10000d) / 10000.f;
                roundedRate = fourCharRate;
            }
        }
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
