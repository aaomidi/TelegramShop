package com.aaomidi.telegramshop.bean.shop;

import com.aaomidi.telegramshop.TelegramShop;
import com.aaomidi.telegramshop.bean.ShopUser;
import com.aaomidi.telegramshop.storage.UserStorage;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import pro.zackpollard.telegrambot.api.chat.message.send.SendableTextMessage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Getter
@Setter
public class Shop {
    private transient final static Pattern SHOP_NAME_VERIFIER = Pattern.compile("^[\\w]{3,16}$");

    @NonNull
    private transient final TelegramShop instance;
    @NonNull
    private final UUID uuid = UUID.randomUUID();
    private final long ownerID;

    private String currencyName = "USD";
    private String currencySymbol = "$";
    private String name;
    private List<ShopItem> shopItemList = Collections.synchronizedList(new ArrayList<>());


    public static boolean verifyShopName(String shopName) {
        Matcher matcher = SHOP_NAME_VERIFIER.matcher(shopName);
        return matcher.matches();
    }


    public ShopItem getItem(int index) {
        return shopItemList.get(index - 1);
    }

    public SendableTextMessage getItemListMessage() {
        StringBuilder builder = new StringBuilder("Items: ");
        int i = 0;
        for (ShopItem item : shopItemList) {
            i++;
            builder.append(String.format("\n\t%d. %s", i, item.getName()));
        }
        return SendableTextMessage.builder().message(builder.toString()).build();
    }

    public void addItem(ShopItem item) {
        shopItemList.add(item);
    }

    public SendableTextMessage getInviteCommand() {
        return SendableTextMessage.builder()
                .message(String.format("Message %s with %n/join@ %s", instance.getBot().getBotUsername(), instance.getBot().getBotUsername(), getUuid().toString()))
                .build();
    }

    public void sendMessageToOwner(String msg) {
        ShopUser user = UserStorage.getUser(ownerID);
        user.sendMessage(msg);
    }
}
