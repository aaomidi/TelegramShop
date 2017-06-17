package com.aaomidi.telegramshop.bean;


import com.aaomidi.telegramshop.TelegramShop;
import com.aaomidi.telegramshop.bean.shop.Shop;
import com.aaomidi.telegramshop.bean.shop.ShopItem;
import com.aaomidi.telegramshop.storage.ShopStorage;
import lombok.Getter;
import lombok.ToString;
import pro.zackpollard.telegrambot.api.chat.message.send.SendableTextMessage;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@ToString
public class ShopUser {
    private final TelegramShop instance;
    @Getter
    private final long userID;
    private final List<UUID> ownedShops = Collections.synchronizedList(new ArrayList<>());
    private final List<UUID> joinedShops = Collections.synchronizedList(new ArrayList<>());

    private final Map<UUID, List<UUID>> purchasedShopItem = new ConcurrentHashMap<>();
    private final Map<UUID, Integer> balances = new ConcurrentHashMap<>();

    private UUID selectedShop;

    public ShopUser(TelegramShop instance, long userID) {
        this.instance = instance;
        this.userID = userID;
    }

    public SendableTextMessage getShopsList() {
        StringBuilder sb = new StringBuilder();
        sb.append("Owned shops:");
        if (ownedShops.isEmpty()) {
            sb.append("\n\tYou have no shops. Use /createshop to make one.");
        } else {
            int i = 0;
            for (UUID shopID : ownedShops) {
                Shop shop = ShopStorage.getShop(shopID);
                i++;
                sb.append(String.format("\n\t%d. %s", i, shop.getName()));
            }
        }
        sb.append("\n\nJoined shops:");
        if (joinedShops.isEmpty()) {
            sb.append("\n\tYou have joined no shops");
        } else {
            int i = 0;
            for (UUID shopID : joinedShops) {
                Shop shop = ShopStorage.getShop(shopID);
                i++;
                sb.append(String.format("\n\t%d. %s", i, shop.getName()));
            }
        }

        return SendableTextMessage.builder().message(sb.toString()).build();
    }

    public void createShop(Shop shop) {
        ShopStorage.storeShop(shop);
        ownedShops.add(shop.getUuid());
        selectShop(shop);
    }

    private void deleteShop() {

    }

    public int getCurrentBalance() {
        if (selectedShop == null) {
            return -1;
        }
        return balances.get(selectedShop);
    }

    public void setCurrentBalance(int amount) {
        if (selectedShop == null) return;
        balances.put(selectedShop, amount);
    }

    public void selectShop(Shop shop) {
        this.selectedShop = shop.getUuid();

    }

    public Shop getSelectedShop() {
        if (selectedShop == null) return null;
        return ShopStorage.getShop(selectedShop);
    }

    public void joinShop(Shop shop) {
        selectShop(shop);
        shop.sendMessageToOwner(instance.getUserCache().getUsername(userID) + " joined your shop.");
        balances.put(shop.getUuid(), 0);
        purchasedShopItem.put(shop.getUuid(), Collections.synchronizedList(new ArrayList<>()));
    }

    public SendableTextMessage purchase(ShopItem item) {
        int cost = item.getCost();

        if (withdraw(cost)) {
            purchasedShopItem.get(item.getShopID()).add(item.getUuid());
            return SendableTextMessage.plain("You bought " + item.getName()).build();
        }
        return SendableTextMessage.plain("You don't have enough money to buy " + item.getName()).build();
    }

    public void sendMessage(String message) {
        instance.getBot().getChat(message).sendMessage(message);
    }

    public boolean withdraw(int amount) {
        int bal = getCurrentBalance();
        if (amount > bal) return false;

        setCurrentBalance(bal - amount);
        return true;
    }

    public boolean deposit(int amount) {
        int bal = getCurrentBalance();

        setCurrentBalance(amount + bal);
        return true;
    }
}
