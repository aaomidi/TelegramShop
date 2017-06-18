package com.aaomidi.telegramshop.commands;

import com.aaomidi.telegramshop.TelegramShop;
import com.aaomidi.telegramshop.bean.ShopUser;
import com.aaomidi.telegramshop.bean.shop.Shop;
import com.aaomidi.telegramshop.storage.UserStorage;
import pro.zackpollard.telegrambot.api.chat.message.Message;
import xyz.nickr.telepad.TelepadBot;
import xyz.nickr.telepad.command.Command;

public class DepositCommand extends Command {
    private final TelegramShop instance;

    public DepositCommand(TelegramShop instance) {
        super("deposit");
        this.instance = instance;
    }

    // deposit @username amount
    @Override
    public void exec(TelepadBot telepadBot, Message message, String[] strings) {
        if (strings.length < 2) {
            return;
        }
        ShopUser user = UserStorage.getUser(message.getSender().getId());

        if (!user.isOwner()) {
            message.getChat().sendMessage("You do not own your current selected shop.");
            return;
        }

        ShopUser otherUser = UserStorage.getUser(instance.getUserCache().getUserId(strings[0]));

        if (otherUser == null) {
            message.getChat().sendMessage("Unrecognized user");
            return;
        }

        Shop shop = user.getSelectedShop();
        if (!otherUser.isPartOfShop(shop.getUuid())) {
            message.getChat().sendMessage("That person is not part of your shop.");
            return;
        }

        int amount = Integer.valueOf(strings[1]);
        if (amount <= 0) {
            message.getChat().sendMessage("You can not deposit a negative amount");
            return;
        }

        otherUser.deposit(amount);

        message.getChat().sendMessage(String.format("Deposited %s%d %s to %s.", shop.getCurrencySymbol(), amount, shop.getCurrencyName(), instance.getUserCache().getUsername(otherUser.getUserID())));
        otherUser.sendMessage(String.format("%s of %s deposited %s%d to you.", user.getUsername(), shop.getName(), shop.getCurrencySymbol(), amount));
    }
}