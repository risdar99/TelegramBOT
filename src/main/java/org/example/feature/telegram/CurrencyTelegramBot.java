package org.example.feature.telegram;






import org.example.feature.command.StartCommand;
import org.example.feature.currency.CurrencyService;
import org.example.feature.currency.NBUCurrencyService;
import org.example.feature.currency.dto.Bank;
import org.example.feature.currency.dto.Currency;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CurrencyTelegramBot extends TelegramLongPollingCommandBot {
    private float roundedRate;
    private float twoCharRate;
    private float threeCharRate;
    private float fourCharRate;
    private List<Currency> currencies;
    private Bank bank;
    public CurrencyTelegramBot(){
        currencies = new ArrayList<>();
        currencies.add(Currency.USD);
        currencies.add(Currency.EUR);
        bank = Bank.NBU;
        register(new StartCommand());
    }
    @Override
    public void processNonCommandUpdate(Update update) {

        if(update.hasCallbackQuery()){
            if (update.getCallbackQuery().getData().equals("Отримати інфо")){
                createGetInfoButton(update);
            }
            if(update.getCallbackQuery().getData().equals("Налаштування")){
                createSettingsButton(update);
            }
            if(update.getCallbackQuery().getData().equals("Кількість знаків після коми")) {
                roundingButton(update);
            }
            if(update.getCallbackQuery().getData().equals("Час оповіщень")) {
                notificationButton(update);
            }

            if(update.getCallbackQuery().getData().equals("Банк")) {
                createBankButton(update);
            }
            if(update.getCallbackQuery().getData().equals("Валюти")) {
                createCurrencyButton(update);
            }
            if(update.getCallbackQuery().getData().equals("USD") || update.getCallbackQuery().getData().equals("✓USD")) {
                makeUsdAsCurrency(update);
            }
            if(update.getCallbackQuery().getData().equals("EUR") || update.getCallbackQuery().getData().equals("✓EUR")) {
                makeEurAsCurrency(update);
            }
            if(update.getCallbackQuery().getData().equals("НБУ")) {
                makeNbuAsBank(update);
            }
            if(update.getCallbackQuery().getData().equals("ПриватБанк")) {
                makePrivatAsBank(update);
            }
            if(update.getCallbackQuery().getData().equals("МоноБанк")) {
                makeMonoAsBank(update);
            }
        }
    }
    private float getUSDFromNBU() {
        CurrencyService currencyService = new NBUCurrencyService();
        double rate = currencyService.getRate(Currency.USD);

        return (float) rate;
    }

    private void createGetInfoButton(Update update)  {
        float usdRate = getUSDFromNBU();
        String startText = "Актуальний курс USD від Національного банку: " + String.format("%.2f", usdRate);

        SendMessage message = new SendMessage();
        message.setText(startText);
        message.setChatId(update.getCallbackQuery().getFrom().getId());

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

        sendApiMethodAsync(message);
    }

    private void createSettingsButton(Update update)  {
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

        String nbu = "НБУ";
        String privat = "ПриватБанк";
        String mono = "МоноБанк";
        if(bank.equals(Bank.NBU)) {
            nbu = "✓НБУ";
        } else if(bank.equals(Bank.PRIVAT)) {
            privat = "✓ПриватБанк";
        } else if(bank.equals(Bank.MONO)) {
            mono = "✓МоноБанк";
        }

        SendMessage message = new SendMessage();
        message.setText(startText);
        message.setChatId(update.getCallbackQuery().getFrom().getId());

        InlineKeyboardButton.InlineKeyboardButtonBuilder nbuButton = InlineKeyboardButton
                .builder()
                .text(nbu)
                .callbackData(nbu);

        InlineKeyboardButton.InlineKeyboardButtonBuilder privatButton = InlineKeyboardButton
                .builder()
                .text(privat)
                .callbackData(privat);

        InlineKeyboardButton.InlineKeyboardButtonBuilder monoButton = InlineKeyboardButton
                .builder()
                .text(mono)
                .callbackData(mono);


        InlineKeyboardMarkup keyboard = InlineKeyboardMarkup
                .builder()
                .keyboard(Collections.singletonList(
                        Arrays.asList(nbuButton.build(), privatButton.build(), monoButton.build())
                ))
                .build();

        message.setReplyMarkup(keyboard);

        sendApiMethodAsync(message);
    }

    private void roundingButton(Update update) {
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

        InlineKeyboardMarkup keyboard1 = InlineKeyboardMarkup
                .builder()
                .keyboard(Collections.singletonList(
                        Arrays.asList(twoButton.build(), threeButton.build(),fourButton.build())
                ))
                .build();

        message.setReplyMarkup(keyboard1);

        sendApiMethodAsync(message);
    }


    private void createCurrencyButton(Update update) {
        String startText = "Оберіть валюти, курс яких ви хочете отримувати: ";

        String usd = "USD";
        String eur = "EUR";
        if(currencies.contains(Currency.USD)) {
            usd = "✓USD";
        }
        if(currencies.contains(Currency.EUR)) {
            eur = "✓EUR";
        }

        SendMessage message = new SendMessage();
        message.setText(startText);
        message.setChatId(update.getCallbackQuery().getFrom().getId());

        InlineKeyboardButton.InlineKeyboardButtonBuilder usdButton = InlineKeyboardButton
                .builder()
                .text(usd)
                .callbackData(usd);

        InlineKeyboardButton.InlineKeyboardButtonBuilder eurButton = InlineKeyboardButton
                .builder()
                .text(eur)
                .callbackData(eur);


        InlineKeyboardMarkup keyboard = InlineKeyboardMarkup
                .builder()
                .keyboard(Collections.singletonList(
                        Arrays.asList(usdButton.build(), eurButton.build())
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

    private void makeUsdAsCurrency(Update update) {
        if(update.getCallbackQuery().getData().equals("USD")) {
            currencies.add(Currency.USD);
        }
        if(update.getCallbackQuery().getData().equals("✓USD")) {
            currencies.remove(Currency.USD);
        }
        createCurrencyButton(update);
    }

    private void makeEurAsCurrency(Update update) {
        if(update.getCallbackQuery().getData().equals("EUR")) {
            currencies.add(Currency.EUR);
        }
        if(update.getCallbackQuery().getData().equals("✓EUR")) {
            currencies.remove(Currency.EUR);
        }
        createCurrencyButton(update);
    }

    private void makeNbuAsBank(Update update) {
        bank = Bank.NBU;
        createBankButton(update);
    }

    private void makePrivatAsBank(Update update) {
        bank = Bank.PRIVAT;
        createBankButton(update);
    }

    private void makeMonoAsBank(Update update) {
        bank = Bank.MONO;
        createBankButton(update);
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