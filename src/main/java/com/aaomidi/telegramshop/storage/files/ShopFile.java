package com.aaomidi.telegramshop.storage.files;

import com.aaomidi.telegramshop.bean.shop.Shop;
import com.aaomidi.telegramshop.storage.ShopStorage;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ShopFile {
    private final List<Shop> shops;

    void prep() {
        for (Shop shop : shops) {
            ShopStorage.storeShop(shop);
        }
    }

}
