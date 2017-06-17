package com.aaomidi.telegramshop;


import com.aaomidi.telegramshop.commands.CreateShopCommand;
import com.aaomidi.telegramshop.commands.ShopsCommand;
import com.aaomidi.telegramshop.storage.UserStorage;
import pro.zackpollard.telegrambot.api.TelegramBot;
import pro.zackpollard.telegrambot.api.event.Listener;
import xyz.nickr.telepad.TelepadBot;

public class TelegramShop implements Listener {
    private final TelepadBot telepadBot;
    private final TelegramBot telegramBot;

    public TelegramShop(String... args) {
        UserStorage.setInstance(this);
        telepadBot = new TelepadBot(args[0]);
        telegramBot = telepadBot.getHandle();
        telepadBot.start(false);

        this.registerCommands();

    }

    public static void main(String[] args) {
        new TelegramShop(args);
    }

    private void registerCommands() {
        telepadBot.getCommandManager().register(new CreateShopCommand(this));
        telepadBot.getCommandManager().register(new ShopsCommand(this));
    }

    public TelegramBot getBot() {
        return telegramBot;
    }
}
