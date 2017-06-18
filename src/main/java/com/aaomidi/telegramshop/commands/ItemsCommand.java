package com.aaomidi.telegramshop.commands;

import com.aaomidi.telegramshop.TelegramShop;
import com.aaomidi.telegramshop.bean.ShopUser;
import com.aaomidi.telegramshop.bean.shop.Shop;
import com.aaomidi.telegramshop.storage.UserStorage;
import pro.zackpollard.telegrambot.api.chat.message.Message;
import xyz.nickr.telepad.TelepadBot;
import xyz.nickr.telepad.command.Command;

public class ItemsCommand extends Command {
    private final TelegramShop instance;

    public ItemsCommand(TelegramShop instance) {
        super("items");
        this.instance = instance;
    }

    @Override
    public void exec(TelepadBot telepadBot, Message message, String[] strings) {
        ShopUser user = UserStorage.getUser(message.getSender().getId());

        Shop shop = user.getSelectedShop();
        if (shop == null) {
            return;
        }
        message.getChat().sendMessage(user.getPurchasedItemsMessage());
    }
}
