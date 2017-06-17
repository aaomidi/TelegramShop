package com.aaomidi.telegramshop.commands;

import com.aaomidi.telegramshop.TelegramShop;
import pro.zackpollard.telegrambot.api.chat.message.Message;
import xyz.nickr.telepad.TelepadBot;
import xyz.nickr.telepad.command.Command;

public class ShopCommand extends Command {
    private final TelegramShop instance;

    public ShopCommand(TelegramShop instance) {
        super("shop");
        this.instance = instance;
    }

    @Override
    public void exec(TelepadBot bot, Message message, String[] args) {
        Message msg = message.getChat().sendMessage("Loading...");
    }
}
