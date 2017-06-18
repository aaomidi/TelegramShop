package com.aaomidi.telegramshop.storage;

import com.aaomidi.telegramshop.TelegramShop;
import com.aaomidi.telegramshop.bean.shop.Shop;
import com.aaomidi.telegramshop.storage.files.ShopFile;
import lombok.Setter;

import java.util.ArrayList;
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
        shop.setInstance(instance);
        shops.put(shop.getUuid(), shop);
    }


    public static ShopFile genFile() {
        return new ShopFile(new ArrayList<>(shops.values()));
    }
}
