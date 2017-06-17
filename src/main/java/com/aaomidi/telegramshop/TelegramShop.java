package com.aaomidi.telegramshop;


import com.aaomidi.telegramshop.bean.ShopUser;
import pro.zackpollard.telegrambot.api.TelegramBot;
import pro.zackpollard.telegrambot.api.chat.message.send.SendableTextMessage;
import pro.zackpollard.telegrambot.api.event.Listener;
import pro.zackpollard.telegrambot.api.event.chat.inline.InlineCallbackQueryReceivedEvent;
import pro.zackpollard.telegrambot.api.event.chat.inline.InlineQueryReceivedEvent;
import pro.zackpollard.telegrambot.api.event.chat.message.CommandMessageReceivedEvent;
import xyz.nickr.telepad.TelepadBot;

public class TelegramShop implements Listener {
    private final TelepadBot telepadBot;
    private final TelegramBot telegramBot;

    public TelegramShop(String... args) {
        telepadBot = new TelepadBot(args[0]);
        telegramBot = telepadBot.getHandle();
        telepadBot.start(false);
        telepadBot.getCommandManager().registerPackage("com.aaomidi.telegramshop.commands");
    }

    public static void main(String[] args) {
        new TelegramShop(args);
    }

    public TelegramBot getBot() {
        return telegramBot;
    }
}
