package com.aaomidi.telegramshop;


import com.aaomidi.telegramshop.commands.*;
import com.aaomidi.telegramshop.storage.ShopStorage;
import com.aaomidi.telegramshop.storage.UserStorage;
import lombok.Getter;
import pro.zackpollard.telegrambot.api.TelegramBot;
import pro.zackpollard.telegrambot.api.event.Listener;
import xyz.nickr.telepad.TelepadBot;
import xyz.nickr.telepad.util.UserCache;

public class TelegramShop implements Listener {
    private final TelepadBot telepadBot;
    private final TelegramBot telegramBot;
    @Getter
    private final UserCache userCache;

    public TelegramShop(String... args) {
        ShopStorage.setInstance(this);
        UserStorage.setInstance(this);

        telepadBot = new TelepadBot(args[0]);
        telegramBot = telepadBot.getHandle();
        telepadBot.start(false);

        userCache = telepadBot.getUserCache();
        this.registerCommands();

    }

    public static void main(String[] args) {
        new TelegramShop(args);
    }

    private void registerCommands() {
        telepadBot.getCommandManager().register(new CreateShopCommand(this));
        telepadBot.getCommandManager().register(new CreateStockCommand(this));
        telepadBot.getCommandManager().register(new InviteCommand(this));
        telepadBot.getCommandManager().register(new StockCommand(this));
        telepadBot.getCommandManager().register(new JoinCommand(this));
        telepadBot.getCommandManager().register(new ShopsCommand(this));
    }

    public TelegramBot getBot() {
        return telegramBot;
    }
}
