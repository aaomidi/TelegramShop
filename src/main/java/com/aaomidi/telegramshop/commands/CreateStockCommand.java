package com.aaomidi.telegramshop.commands;


import com.aaomidi.telegramshop.TelegramShop;
import com.aaomidi.telegramshop.bean.ShopUser;
import com.aaomidi.telegramshop.bean.shop.ShopItem;
import com.aaomidi.telegramshop.storage.UserStorage;
import pro.zackpollard.telegrambot.api.chat.message.Message;
import xyz.nickr.telepad.TelepadBot;
import xyz.nickr.telepad.command.Command;

public class CreateStockCommand extends Command {

    private final TelegramShop instance;

    public CreateStockCommand(TelegramShop instance) {
        super("createstock");
        this.instance = instance;
    }

    //createstock name cost time description
    @Override
    public void exec(TelepadBot telepadBot, Message message, String[] strings) {
        if (strings.length < 4) {
            return;
        }
        ShopUser user = UserStorage.getUser(message.getSender().getId());

        if (user.getSelectedShop() == null) {
            return;
        }

        String name = strings[0];
        int cost = Integer.valueOf(strings[1]);
        long time = Long.valueOf(strings[2]);
        StringBuilder descriptionBuilder = new StringBuilder();

        for (int i = 3; i < strings.length; i++) {
            descriptionBuilder.append(strings[i]);
            descriptionBuilder.append(" ");
        }
        String description = descriptionBuilder.toString().trim();

        if (!ShopItem.verifyItemName(name)) {
            message.getChat().sendMessage("Item name had a problem.");
            return;
        }

        ShopItem item = new ShopItem(user.getSelectedShop().getUuid(), name, description, time, cost);
        user.getSelectedShop().addItem(item);

        message.getChat().sendMessage("Added stock.");
    }
}
