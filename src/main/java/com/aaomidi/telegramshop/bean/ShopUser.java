package com.aaomidi.telegramshop.bean;


import com.aaomidi.telegramshop.TelegramShop;
import com.aaomidi.telegramshop.bean.shop.Shop;
import com.aaomidi.telegramshop.bean.shop.Stock;
import lombok.Getter;
import lombok.ToString;
import pro.zackpollard.telegrambot.api.chat.message.send.SendableTextMessage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ToString
public class ShopUser {
    private final TelegramShop instance;
    @Getter
    private final long userID;
    private final List<Shop> ownedShops = Collections.synchronizedList(new ArrayList<>());
    private final List<Shop> joinedShops = Collections.synchronizedList(new ArrayList<>());
    private final List<Stock> purchasedStock = Collections.synchronizedList(new ArrayList<>());

    private Shop selectedShop;

    public ShopUser(TelegramShop instance, long userID) {
        this.instance = instance;
        this.userID = userID;
    }

    public SendableTextMessage getShopsList() {
        StringBuilder sb = new StringBuilder();
        sb.append("Owned shops:");
        if (ownedShops.isEmpty()) {
            sb.append("\tYou have no shops. Use /createshop to make one.");
        } else {
            int i = 0;
            for (Shop shop : ownedShops) {
                i++;
                sb.append(String.format("\t%d. %s", i, shop.getName()));
            }
        }
        sb.append("Joined shops:");
        if (joinedShops.isEmpty()) {
            sb.append("\tYou have joined no shops");
        } else {
            int i = 0;
            for (Shop shop : joinedShops) {
                i++;
                sb.append(String.format("\t%d. %s", i, shop.getName()));
            }
        }

        return SendableTextMessage.builder().message(sb.toString()).build();
    }

    private void createShop(Shop shop) {

    }

    private void deleteShop() {

    }

    private void selectShop(Shop shop) {
        this.selectedShop = shop;

    }

    private void addStock() {

    }

    private void purchase() {

    }

    private void sendMessage(String format, String... args) {
        String message = String.format(format, args);
    }
}
