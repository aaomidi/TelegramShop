package com.aaomidi.telegramshop.bean.shop;

import com.aaomidi.telegramshop.TelegramShop;
import com.aaomidi.telegramshop.bean.ShopUser;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

@Getter
@Builder
public class Shop {
    public transient final static Pattern SHOP_NAME_VERIFIER = Pattern.compile("^[\\w]{0,16}$");

    @NonNull
    private transient final TelegramShop instance;
    @NonNull
    @Builder.Default
    private final UUID uuid = UUID.randomUUID();
    @NonNull
    @Builder.Default
    private String currencyName = "USD";
    @Builder.Default
    private String currencySymbol = "$";
    private String shopName;
    @Builder.Default
    private List<Stock> stockList = Collections.synchronizedList(new ArrayList<>());
    @Builder.Default
    private Map<ShopUser, Integer> balances = new ConcurrentHashMap<>();

}
