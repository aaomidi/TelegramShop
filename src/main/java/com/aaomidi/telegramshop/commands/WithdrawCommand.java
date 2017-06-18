package com.aaomidi.telegramshop.commands;


import com.aaomidi.telegramshop.TelegramShop;
import com.aaomidi.telegramshop.bean.ShopUser;
import com.aaomidi.telegramshop.bean.shop.Shop;
import com.aaomidi.telegramshop.storage.UserStorage;
import pro.zackpollard.telegrambot.api.chat.message.Message;
import xyz.nickr.telepad.TelepadBot;
import xyz.nickr.telepad.command.Command;

public class WithdrawCommand extends Command {
    private final TelegramShop instance;

    public WithdrawCommand(TelegramShop instance) {
        super("withdraw");
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

        otherUser.unsafeWithdraw(amount);
        message.getChat().sendMessage(String.format("Withdrew %s%d %s from %s.", shop.getCurrencySymbol(), amount, shop.getCurrencyName(), otherUser.getUsername()));
        otherUser.sendMessage(String.format("%s of %s withdrew %s%d from you.", user.getUsername(), shop.getName(), shop.getCurrencySymbol(), amount));
    }
}
