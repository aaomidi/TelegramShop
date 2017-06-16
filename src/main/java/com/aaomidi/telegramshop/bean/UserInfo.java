package com.aaomidi.telegramshop.bean;

import lombok.Data;
import pro.zackpollard.telegrambot.api.user.User;

@Data
public class UserInfo {
    private final long userID;
    private final String username;
    private final String fullName;

    public static UserInfo fromUser(User user) {
        return new UserInfo(user.getId(), user.getUsername(), user.getFullName());
    }
}
