package com.aaomidi.telegramshop.bean.shop;

import lombok.Builder;
import lombok.ToString;

@ToString
@Builder
public class Stock {
    private final Shop shop;
    private final String name;
    private final String description;
    // In seconds
    private final long duration;
    private final int cost;
}
