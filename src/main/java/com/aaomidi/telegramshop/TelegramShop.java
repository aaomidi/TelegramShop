package com.aaomidi.telegramshop;


import com.aaomidi.telegramshop.commands.*;
import com.aaomidi.telegramshop.storage.ShopStorage;
import com.aaomidi.telegramshop.storage.UserStorage;
import com.aaomidi.telegramshop.storage.files.FileStorage;
import lombok.Getter;
import pro.zackpollard.telegrambot.api.TelegramBot;
import pro.zackpollard.telegrambot.api.event.Listener;
import xyz.nickr.telepad.TelepadBot;
import xyz.nickr.telepad.util.UserCache;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TelegramShop implements Listener {
    public final static ScheduledExecutorService scheduledService = Executors.newScheduledThreadPool(1);

    private final TelepadBot telepadBot;
    private final TelegramBot telegramBot;
    @Getter
    private final UserCache userCache;
    private final FileStorage storage;

    public TelegramShop(String... args) {
        ShopStorage.setInstance(this);
        UserStorage.setInstance(this);

        storage = new FileStorage(this);
        storage.readFiles();

        telepadBot = new TelepadBot(args[0]);
        telegramBot = telepadBot.getHandle();
        telepadBot.start(false);

        userCache = telepadBot.getUserCache();
        this.registerCommands();
        regularSaving();

    }

    public static void main(String[] args) {
        new TelegramShop(args);

    }

    private void registerCommands() {
        telepadBot.getCommandManager().register(new BalanceCommand(this));
        telepadBot.getCommandManager().register(new CreateShopCommand(this));
        telepadBot.getCommandManager().register(new CreateStockCommand(this));
        telepadBot.getCommandManager().register(new DepositCommand(this));
        telepadBot.getCommandManager().register(new InviteCommand(this));
        telepadBot.getCommandManager().register(new ItemsCommand(this));
        telepadBot.getCommandManager().register(new JoinCommand(this));
        telepadBot.getCommandManager().register(new PurchaseCommand(this));
        telepadBot.getCommandManager().register(new ShopsCommand(this));
        telepadBot.getCommandManager().register(new StockCommand(this));
        telepadBot.getCommandManager().register(new WithdrawCommand(this));
    }

    private void regularSaving() {
        scheduledService.scheduleAtFixedRate(storage::saveFiles, 10L, 10L, TimeUnit.SECONDS);
    }

    public TelegramBot getBot() {
        return telegramBot;
    }
}
