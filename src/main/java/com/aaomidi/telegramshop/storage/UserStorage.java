package com.aaomidi.telegramshop.storage;

import com.aaomidi.telegramshop.TelegramShop;
import com.aaomidi.telegramshop.bean.ShopUser;
import lombok.Setter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserStorage {
    @Setter
    private static TelegramShop instance;
    private static Map<Long, ShopUser> userHashMap = new ConcurrentHashMap<>();

    public static ShopUser getUser(long userID) {
        return userHashMap.computeIfAbsent(userID, id -> new ShopUser(instance, id));
    }

    public static void storeUser(ShopUser user) {
        userHashMap.put(user.getUserID(), user);
    }
}
