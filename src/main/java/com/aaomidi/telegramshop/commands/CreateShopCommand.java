package com.aaomidi.telegramshop.commands;

import com.aaomidi.telegramshop.TelegramShop;
import pro.zackpollard.telegrambot.api.chat.message.Message;
import xyz.nickr.telepad.TelepadBot;
import xyz.nickr.telepad.command.Command;

public class CreateShopCommand extends Command {
    private final TelegramShop instance;

    public CreateShopCommand(TelegramShop instance) {
        super("createshop");
        this.instance = instance;
    }

    // createshop
    @Override
    public void exec(TelepadBot telepadBot, Message message, String[] strings) {

    }
}
