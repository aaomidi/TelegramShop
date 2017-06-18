package com.aaomidi.telegramshop.storage;

import com.aaomidi.telegramshop.TelegramShop;
import com.aaomidi.telegramshop.bean.ShopUser;
import com.aaomidi.telegramshop.storage.files.UserFile;
import lombok.Setter;

import java.util.ArrayList;
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
        user.setInstance(instance);
        userHashMap.put(user.getUserID(), user);
    }

    public static UserFile genFile() {
        return new UserFile(new ArrayList<>(userHashMap.values()));
    }
}
