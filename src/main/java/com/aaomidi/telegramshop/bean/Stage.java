package com.aaomidi.telegramshop.bean;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;

@Getter
@RequiredArgsConstructor
public enum Stage {
    SELECT_SHOP(1);
    public static HashMap<Integer, Stage> map;

    static {
        map = new HashMap<>();

        for (Stage s : Stage.values()) {
            map.put(s.getMagic(), s);
        }
    }

    private final int magic;

    public static Stage getFromMagic(int x) {
        return map.get(x);
    }
}
