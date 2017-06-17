package com.aaomidi.telegramshop.storage;

import com.aaomidi.telegramshop.TelegramShop;
import com.aaomidi.telegramshop.bean.shop.Shop;
import lombok.Setter;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ShopStorage {
    public static Map<UUID, Shop> shops = new ConcurrentHashMap<>();
    @Setter
    private static TelegramShop instance;

    public static Shop getShop(UUID uuid) {
        return shops.get(uuid);
    }

    public static void storeShop(Shop shop) {
        shops.put(shop.getUuid(), shop);
    }
}
