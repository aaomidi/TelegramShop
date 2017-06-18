package com.aaomidi.telegramshop.storage.files;

import com.aaomidi.telegramshop.bean.ShopUser;
import com.aaomidi.telegramshop.storage.UserStorage;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class UserFile {
    private final List<ShopUser> users;

    void prep() {
        if (users == null) return;
        for (ShopUser user : users) {
            UserStorage.storeUser(user);
        }
    }

}
