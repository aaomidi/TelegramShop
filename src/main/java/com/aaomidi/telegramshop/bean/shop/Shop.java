package com.aaomidi.telegramshop.bean.shop;

import com.aaomidi.telegramshop.TelegramShop;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Getter
public class Shop {
    public transient final static Pattern SHOP_NAME_VERIFIER = Pattern.compile("^[\\w]{0,16}$");

    @NonNull
    private transient final TelegramShop instance;
    @NonNull
    private final UUID uuid = UUID.randomUUID();
    private String currencyName = "USD";
    private String currencySymbol = "$";
    private String name;
    private List<Stock> stockList = Collections.synchronizedList(new ArrayList<>());
    private Map<Long, Integer> balances = new ConcurrentHashMap<>();


}
