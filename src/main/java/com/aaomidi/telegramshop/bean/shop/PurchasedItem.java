package com.aaomidi.telegramshop.bean.shop;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class PurchasedItem {
    private final UUID uuid;
    private final long purchasedTime = System.currentTimeMillis();
}
