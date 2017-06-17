package com.aaomidi.telegramshop.storage;

import com.aaomidi.telegramshop.bean.ShopUser;

import java.util.HashMap;

public class UserStorage {
    private static HashMap<Long, ShopUser> userHashMap = new HashMap<>();

    public static ShopUser getUser(long userID) {
        return userHashMap.get(userID);
    }

    public static void storeUser(ShopUser user) {
        userHashMap.put(user.getUserID(), user);
    }
}
