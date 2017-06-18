package com.aaomidi.telegramshop.commands;


import com.aaomidi.telegramshop.TelegramShop;
import com.aaomidi.telegramshop.bean.ShopUser;
import com.aaomidi.telegramshop.storage.UserStorage;
import pro.zackpollard.telegrambot.api.chat.message.Message;
import xyz.nickr.telepad.TelepadBot;
import xyz.nickr.telepad.command.Command;

public class BalanceCommand extends Command {
    private final TelegramShop instance;

    public BalanceCommand(TelegramShop instance) {
        super("balance");
        this.instance = instance;
    }

    @Override
    public void exec(TelepadBot telepadBot, Message message, String[] strings) {
        ShopUser user = UserStorage.getUser(message.getSender().getId());

        if (user.getSelectedShop() == null) {
            return;
        }
        message.getChat().sendMessage("Your balance is: " + user.getSelectedShop().getCurrencySymbol() + user.getCurrentBalance());

    }
}
