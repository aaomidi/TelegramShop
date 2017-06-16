package com.aaomidi.telegramshop;


import com.aaomidi.telegramshop.bean.ShopUser;
import pro.zackpollard.telegrambot.api.TelegramBot;
import pro.zackpollard.telegrambot.api.event.Listener;
import pro.zackpollard.telegrambot.api.event.chat.inline.InlineCallbackQueryReceivedEvent;
import pro.zackpollard.telegrambot.api.event.chat.inline.InlineQueryReceivedEvent;
import pro.zackpollard.telegrambot.api.event.chat.message.CommandMessageReceivedEvent;

public class TelegramShop implements Listener {
    private final TelegramBot telegramBot;

    public TelegramShop(String... args) {
        telegramBot = TelegramBot.login(args[0]);
        telegramBot.getEventsManager().register(this);

        telegramBot.startUpdates(false);
    }

    public static void main(String[] args) {
        new TelegramShop(args);
    }

    public TelegramBot getBot() {
        return telegramBot;
    }

    @Override
    public void onCommandMessageReceived(CommandMessageReceivedEvent event) {
        ShopUser shopUser = new ShopUser(this, event.getMessage().getSender());

    }

    @Override
    public void onInlineQueryReceived(InlineQueryReceivedEvent event) {

    }

    @Override
    public void onInlineCallbackQueryReceivedEvent(InlineCallbackQueryReceivedEvent event) {

    }
}
