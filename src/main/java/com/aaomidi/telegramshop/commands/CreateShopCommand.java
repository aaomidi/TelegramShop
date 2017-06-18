package com.aaomidi.telegramshop.commands;

import com.aaomidi.telegramshop.TelegramShop;
import com.aaomidi.telegramshop.bean.ShopUser;
import com.aaomidi.telegramshop.bean.shop.Shop;
import com.aaomidi.telegramshop.storage.UserStorage;
import pro.zackpollard.telegrambot.api.chat.message.Message;
import xyz.nickr.telepad.TelepadBot;
import xyz.nickr.telepad.command.Command;

public class CreateShopCommand extends Command {
    private final TelegramShop instance;

    public CreateShopCommand(TelegramShop instance) {
        super("createshop");
        this.instance = instance;
    }

    // createshop name currencyName currencySymbol
    @Override
    public void exec(TelepadBot telepadBot, Message message, String[] strings) {
        ShopUser user = UserStorage.getUser(message.getSender().getId());

        if (strings.length < 3) {
            // do nothing
            return;
        }

        String name = strings[0];
        String currencyName = strings[1];
        String currencySymbol = strings[2];

        if (!Shop.verifyShopName(name)) {

            message.getChat().sendMessage("Shop name had a problem.");
            return;
        }

        Shop shop = new Shop(user.getUserID(), instance);
        shop.setName(name);
        shop.setCurrencyName(currencyName);
        shop.setCurrencySymbol(currencySymbol);

        user.createShop(shop);

        message.getChat().sendMessage("Shop created and selected!");
    }
}
