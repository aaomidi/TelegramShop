package com.aaomidi.telegramshop.bean.shop;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ToString
@RequiredArgsConstructor
@Getter
public class ShopItem {
    private transient final static Pattern SHOP_NAME_VERIFIER = Pattern.compile("^[\\w]{3,16}$");
    private final UUID uuid = UUID.randomUUID();
    private final UUID shopID;
    private final String name;
    private final String description;
    // In seconds
    private final long duration;
    private final int cost;

    public static boolean verifyItemName(String shopName) {
        Matcher matcher = SHOP_NAME_VERIFIER.matcher(shopName);
        return matcher.matches();
    }
}
