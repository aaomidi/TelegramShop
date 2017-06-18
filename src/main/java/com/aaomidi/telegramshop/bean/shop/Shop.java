package com.aaomidi.telegramshop.bean.shop;

import com.aaomidi.telegramshop.TelegramShop;
import com.aaomidi.telegramshop.bean.ShopUser;
import com.aaomidi.telegramshop.storage.UserStorage;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import pro.zackpollard.telegrambot.api.chat.message.send.SendableTextMessage;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Getter
@Setter
public class Shop {
    private transient final static Pattern SHOP_NAME_VERIFIER = Pattern.compile("^[\\w]{3,16}$");
    @NonNull
    private final UUID uuid = UUID.randomUUID();
    private final long ownerID;
    @NonNull
    @Setter
    private transient TelegramShop instance;
    private String currencyName = "USD";
    private String currencySymbol = "$";
    private String name;
    private Map<UUID, ShopItem> shopItems = new ConcurrentHashMap<>();


    private transient List<ShopItem> shopItemList;

    public static boolean verifyShopName(String shopName) {
        Matcher matcher = SHOP_NAME_VERIFIER.matcher(shopName);
        return matcher.matches();
    }

    public List<ShopItem> getShopItemList() {
        if (shopItemList == null) {
            shopItemList = new CopyOnWriteArrayList<>();
            shopItemList.addAll(shopItems.values());
        }
        return shopItemList;
    }

    public ShopItem getItem(int index) {
        return getShopItemList().get(index);
    }

    public ShopItem getItem(UUID uuid) {
        return getShopItems().get(uuid);
    }

    public SendableTextMessage getItemListMessage() {
        StringBuilder builder = new StringBuilder("Items: ");
        int i = 0;
        for (ShopItem item : getShopItemList()) {
            i++;
            builder.append(String.format("\n\t%d. %s - %s%d", i, item.getName(), item.getShop().getCurrencySymbol(), item.getCost()));
        }
        return SendableTextMessage.builder().message(builder.toString()).build();
    }

    public void addItem(ShopItem item) {
        shopItems.put(item.getUuid(), item);
        getShopItemList().add(item);
    }

    public SendableTextMessage getInviteCommand() {
        return SendableTextMessage.builder()
                .message(String.format("Message %s with %n/join%s %s", instance.getBot().getBotUsername(), instance.getBot().getBotUsername(), getUuid().toString()))
                .build();
    }

    public void sendMessageToOwner(String msg) {
        ShopUser user = UserStorage.getUser(ownerID);
        user.sendMessage(msg);
    }
}
