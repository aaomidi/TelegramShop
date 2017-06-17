package com.aaomidi.telegramshop.commands;

import com.aaomidi.telegramshop.TelegramShop;
import com.aaomidi.telegramshop.bean.ShopUser;
import com.aaomidi.telegramshop.storage.UserStorage;
import pro.zackpollard.telegrambot.api.chat.message.Message;
import xyz.nickr.telepad.TelepadBot;
import xyz.nickr.telepad.command.Command;

public class ShopsCommand extends Command {
    private final TelegramShop instance;

    public ShopsCommand(TelegramShop instance) {
        super("shops");
        this.instance = instance;
    }

    @Override
    public void exec(TelepadBot bot, Message message, String[] args) {
        ShopUser user = UserStorage.getUser(message.getSender().getId());
        message.getChat().sendMessage(user.getShopsList());
    }
}
