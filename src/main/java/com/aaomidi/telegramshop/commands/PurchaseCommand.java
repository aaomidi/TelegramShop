package com.aaomidi.telegramshop.commands;

import com.aaomidi.telegramshop.TelegramShop;
import com.aaomidi.telegramshop.bean.ShopUser;
import com.aaomidi.telegramshop.bean.shop.ShopItem;
import com.aaomidi.telegramshop.storage.UserStorage;
import pro.zackpollard.telegrambot.api.chat.message.Message;
import xyz.nickr.telepad.TelepadBot;
import xyz.nickr.telepad.command.Command;

public class PurchaseCommand extends Command {
    private final TelegramShop instance;

    public PurchaseCommand(TelegramShop instance) {
        super("purchase");
        this.instance = instance;
    }

    @Override
    public void exec(TelepadBot telepadBot, Message message, String[] strings) {
        if (strings.length < 1) {
            return;
        }

        ShopUser user = UserStorage.getUser(message.getSender().getId());

        if (user.getSelectedShop() == null) {
            return;
        }
        int index = Integer.valueOf(strings[0]);

        ShopItem item = user.getSelectedShop().getItem(index);

        if (item == null) {
            return;
        }

        user.purchase(item);
    }
}
