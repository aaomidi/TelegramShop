package com.aaomidi.telegramshop.commands;


import com.aaomidi.telegramshop.TelegramShop;
import com.aaomidi.telegramshop.bean.ShopUser;
import com.aaomidi.telegramshop.bean.shop.Shop;
import com.aaomidi.telegramshop.storage.ShopStorage;
import com.aaomidi.telegramshop.storage.UserStorage;
import pro.zackpollard.telegrambot.api.chat.message.Message;
import xyz.nickr.telepad.TelepadBot;
import xyz.nickr.telepad.command.Command;

import java.util.UUID;

public class JoinCommand extends Command {
    private final TelegramShop instance;

    public JoinCommand(TelegramShop instance) {
        super("join");
        this.instance = instance;
    }

    @Override
    public void exec(TelepadBot telepadBot, Message message, String[] strings) {
        if (strings.length < 1) {
            return;
        }
        ShopUser user = UserStorage.getUser(message.getSender().getId());

        UUID uuid = UUID.fromString(strings[0]);
        Shop shop = ShopStorage.getShop(uuid);

        user.joinShop(shop);
        message.getChat().sendMessage(String.format("Joined %s's shop.", instance.getUserCache().getUsername(shop.getOwnerID())));
    }
}
