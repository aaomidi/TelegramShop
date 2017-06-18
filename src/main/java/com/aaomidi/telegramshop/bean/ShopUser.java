package com.aaomidi.telegramshop.bean;


import com.aaomidi.telegramshop.TelegramShop;
import com.aaomidi.telegramshop.bean.shop.PurchasedItem;
import com.aaomidi.telegramshop.bean.shop.Shop;
import com.aaomidi.telegramshop.bean.shop.ShopItem;
import com.aaomidi.telegramshop.storage.ShopStorage;
import lombok.Setter;
import lombok.ToString;
import pro.zackpollard.telegrambot.api.chat.message.send.SendableTextMessage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@ToString
public class ShopUser {
    private transient static TimeZone tz = TimeZone.getTimeZone("UTC");
    private transient static DateFormat df = new SimpleDateFormat("yyy-MM-dd HH:mm:ss z");

    static {
        df.setTimeZone(tz);
    }

    private final long userID;
    private final List<UUID> ownedShops = Collections.synchronizedList(new ArrayList<>());
    private final List<UUID> joinedShops = Collections.synchronizedList(new ArrayList<>());
    private final Map<UUID, List<PurchasedItem>> purchasedShopItem = new ConcurrentHashMap<>();
    private final Map<UUID, Integer> balances = new ConcurrentHashMap<>();
    @Setter
    private transient TelegramShop instance;
    private UUID selectedShop;

    public ShopUser(TelegramShop instance, long userID) {
        this.instance = instance;
        this.userID = userID;
    }

    public long getUserID() {
        return userID;
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
        joinedShops.add(shop.getUuid());
        selectShop(shop);
        shop.sendMessageToOwner(instance.getUserCache().getUsername(userID) + " joined your shop.");
        balances.put(shop.getUuid(), 0);
        purchasedShopItem.put(shop.getUuid(), Collections.synchronizedList(new ArrayList<>()));
    }

    public SendableTextMessage purchase(ShopItem item) {
        int cost = item.getCost();
        if (withdraw(cost)) {
            purchasedShopItem.get(item.getShopID()).add(new PurchasedItem(item.getUuid()));

            item.getShop().sendMessageToOwner(String.format("%s just bought %s", instance.getUserCache().getUsername(userID), item.getName()));
            return SendableTextMessage.plain("You bought " + item.getName()).build();
        }
        return SendableTextMessage.plain("You don't have enough money to buy " + item.getName()).build();
    }

    public void sendMessage(String message) {
        instance.getBot().getChat(userID
        ).sendMessage(message);
    }

    public boolean isOwner() {
        Shop shop = getSelectedShop();
        if (shop == null) return false;

        return userID == shop.getOwnerID();
    }

    public boolean withdraw(int amount) {
        int bal = getCurrentBalance();
        if (amount > bal) return false;

        setCurrentBalance(bal - amount);
        return true;
    }

    public void unsafeWithdraw(int amount) {
        int bal = getCurrentBalance();

        setCurrentBalance(bal - amount);
    }

    public boolean deposit(int amount) {
        int bal = getCurrentBalance();

        setCurrentBalance(amount + bal);
        return true;
    }

    public boolean isPartOfShop(UUID shopID) {
        return joinedShops.contains(shopID);
    }

    private void cleanupItems() {
        Set<UUID> remove = new HashSet<>();
        for (UUID uuid : purchasedShopItem.keySet()) {
            Shop shop = ShopStorage.getShop(uuid);
            if (shop == null) {
                remove.add(uuid);
                continue;
            }
            cleanupItems(shop);
        }

        for (UUID uuid : remove) {
            purchasedShopItem.remove(uuid);
        }
    }

    private void cleanupItems(Shop shop) {
        long now = System.currentTimeMillis();

        List<PurchasedItem> purchasedItems = purchasedShopItem.get(shop.getUuid());
        if (purchasedItems == null) return;

        Iterator<PurchasedItem> iterator = purchasedItems.iterator();
        while (iterator.hasNext()) {
            PurchasedItem purchasedItem = iterator.next();
            ShopItem item = shop.getItem(purchasedItem.getUuid());
            if (item == null) {
                iterator.remove();
                continue;
            }

            long duration = item.getDuration();
            if (duration <= 0) duration = 60 * 60 * 24;
            duration *= 1000; //milli

            long purchaseTime = purchasedItem.getPurchasedTime();

            if (now - purchaseTime > duration) {
                iterator.remove();
            }
        }
    }

    public List<PurchasedItem> getPurchasedItems() {
        Shop shop = getSelectedShop();
        if (shop == null) {
            return new ArrayList<>();
        }
        cleanupItems(shop);

        return purchasedShopItem.get(shop.getUuid());
    }

    public SendableTextMessage getPurchasedItemsMessage() {
        Shop shop = getSelectedShop();
        if (shop == null) return SendableTextMessage.plain("No items").build();

        SendableTextMessage.SendableTextMessageBuilder builder = SendableTextMessage.builder();
        StringBuilder sb = new StringBuilder("Purchased items:");
        int i = 0;

        for (PurchasedItem purchasedItem : getPurchasedItems()) {
            i++;
            ShopItem shopItem = shop.getItem(purchasedItem.getUuid());

            long duration = shopItem.getDuration();
            String time;
            if (duration <= 0) {
                time = "one time use";
            } else {
                time = "expires on ";
                time += df.format(new Date(purchasedItem.getPurchasedTime() + (duration * 1000)));
            }

            sb.append(String.format("\n\t%d. %s - %s", i, shop.getItem(purchasedItem.getUuid()).getName(), time));
        }

        return SendableTextMessage.builder().message(sb.toString()).build();
    }

    public String getUsername() {
        return instance.getUserCache().getUsername(userID);
    }
}
