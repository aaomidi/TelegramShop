package com.aaomidi.telegramshop.bean;


import com.aaomidi.telegramshop.TelegramShop;
import com.aaomidi.telegramshop.bean.shop.Shop;
import com.aaomidi.telegramshop.bean.shop.Stock;
import com.aaomidi.telegramshop.util.Strings;
import lombok.ToString;
import pro.zackpollard.telegrambot.api.chat.inline.send.content.InputTextMessageContent;
import pro.zackpollard.telegrambot.api.chat.inline.send.results.InlineQueryResult;
import pro.zackpollard.telegrambot.api.chat.inline.send.results.InlineQueryResultArticle;
import pro.zackpollard.telegrambot.api.chat.message.send.ParseMode;
import pro.zackpollard.telegrambot.api.keyboards.InlineKeyboardButton;
import pro.zackpollard.telegrambot.api.keyboards.InlineKeyboardMarkup;
import pro.zackpollard.telegrambot.api.menu.InlineMenu;
import pro.zackpollard.telegrambot.api.menu.InlineMenuBuilder;
import pro.zackpollard.telegrambot.api.menu.InlineMenuRowBuilder;
import pro.zackpollard.telegrambot.api.menu.SubInlineMenuBuilder;
import pro.zackpollard.telegrambot.api.user.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;

@ToString
public class ShopUser {
    private final TelegramShop instance;
    private final UserInfo userInfo;
    private final List<Shop> ownedShops = Collections.synchronizedList(new ArrayList<>());
    private final List<Shop> joinedShops = Collections.synchronizedList(new ArrayList<>());
    private final List<Stock> purchasedStock = Collections.synchronizedList(new ArrayList<>());

    private Shop selectedShop;

    public ShopUser(TelegramShop instance, User user) {
        this.instance = instance;
        this.userInfo = UserInfo.fromUser(user);
    }

    private void createShop() {

    }

    private void deleteShop() {

    }

    private void selectShop(Shop shop) {
        this.selectedShop = shop;

    }

    private void addStock() {

    }

    private void purchase() {

    }

    public InlineQueryResult getInline() {
        return InlineQueryResultArticle.builder()
                .title("Shop Menu")
                .description("Click to open your shop.")
                .inputMessageContent(
                        InputTextMessageContent.builder()
                                .messageText("*Shop Control Panel*")
                                .parseMode(ParseMode.MARKDOWN)
                                .build())
                .replyMarkup(InlineKeyboardMarkup.builder()
                        .addRow(
                                buildShopRow()
                        ).build())
                .build();

    }

    public InlineKeyboardButton buildShopRow() {
        InlineKeyboardButton.InlineKeyboardButtonBuilder builder = InlineKeyboardButton.builder();
        if (selectedShop == null) {
            builder.text("Select a shop").callbackData(Stage.SELECT_SHOP.toString()).build();
        }

        return builder.build();
    }

    /*public void startMenu(Chat chat) {
        InlineMenuBuilder builder = InlineMenu.builder(instance.getBot(), chat)
                .allowedUser(userInfo.getUserID())
                .message(SendableTextMessage.markdown("*User control panel*"));

        buildShopRow(builder)
                .build()
                .buildMenu()
                .start();

    } */

   /* private InlineMenuRowBuilder<InlineMenuBuilder> buildShopRow(InlineMenuBuilder builder) {
        InlineMenuRowBuilder<InlineMenuBuilder> rowBuilder = builder.newRow();
        InlineMenu shopSelectionSubMenu = getShopsSelectionOverviewMenu(builder);
        if (selectedShop == null) {
            rowBuilder.menuButton().text("Select a shop").nextMenu(shopSelectionSubMenu).build();
        } else {
            rowBuilder.menuButton().text(String.format("Selected shop: %s", selectedShop.getShopName())).nextMenu(shopSelectionSubMenu).build();
        }
        return rowBuilder;
    } */

    private InlineMenu getShopsSelectionOverviewMenu(InlineMenuBuilder builder) {
        InlineMenuRowBuilder<SubInlineMenuBuilder> rowBuilder = builder.subMenu()
                .allowedUser(userInfo.getUserID())
                .newRow();

        if (!ownedShops.isEmpty()) {
            InlineMenu shopSelectionSubMenu = getShopsSelectionMenu(builder, true);

            rowBuilder.menuButton().text("Your shops.").nextMenu(shopSelectionSubMenu).build();
        } else {
            InlineMenu shopCreationMenu = getShopCreationMenu(builder);
            rowBuilder.menuButton("You have no shops. Click to make one.").nextMenu(shopCreationMenu).build();
        }

        if (!joinedShops.isEmpty()) {
            InlineMenu shopSelectionSubMenu = getShopsSelectionMenu(builder, false);
            rowBuilder = rowBuilder.menuButton().text("Others' shops.").nextMenu(shopSelectionSubMenu).build();
        } else {
            rowBuilder = rowBuilder.backButton("You have joined no shops.").build();
        }

        return rowBuilder.build().buildMenu();
    }

    private InlineMenu getShopsSelectionMenu(InlineMenuBuilder builder, boolean isOwned) {
        InlineMenuRowBuilder<SubInlineMenuBuilder> rowBuilder = builder.subMenu()
                .allowedUser(userInfo.getUserID())
                //.message(SendableTextMessage.markdown("*Select an option*"))
                .newRow();

        List<Shop> shops;
        if (isOwned) {
            shops = ownedShops;

        } else {
            shops = joinedShops;
        }
        for (Shop shop : shops) {
            String name = shop.getShopName();
            boolean selected;

            if (selectedShop == null) {
                selected = false;
            } else {
                selected = selectedShop.equals(shop);
            }

            if (selected) {
                // 🔵
                name = Strings.BLUE_CIRCLE + name;
            } else {
                // 🔴
                name = Strings.RED_CIRCLE + name;
            }


            rowBuilder = rowBuilder.backButton(name).buttonCallback(inlineMenuButton -> {
                if (selected) {
                    selectShop(null);
                } else {
                    selectShop(shop);
                }
                return null;
            }).build();
        }

        InlineMenu shopCreationMenu = getShopCreationMenu(builder);
        if (isOwned) {
            rowBuilder.menuButton().text("Create your shop").nextMenu(shopCreationMenu);
        }
        return rowBuilder.build().buildMenu();
    }

    private InlineMenu getShopCreationMenu(InlineMenuBuilder builder) {
        Shop.ShopBuilder shopBuilder = Shop.builder();
        shopBuilder.instance(instance);
        InlineMenuRowBuilder<SubInlineMenuBuilder> rowBuilder = builder.subMenu()
                .newRow()

                .inputButton().text(Strings.RED_CIRCLE + " Shop Name")
                .buttonCallback(button -> "Enter name: ")
                .textCallback((inlineMenuButton, s) -> {
                    Matcher matcher = Shop.SHOP_NAME_VERIFIER.matcher(s);
                    if (!matcher.matches()) {
                        shopBuilder.shopName(null);
                        inlineMenuButton.setText("English characters only!");
                        return;
                    }
                    inlineMenuButton.setText(Strings.BLUE_CIRCLE + " " + s);
                    shopBuilder.shopName(s);
                })
                .build()

                .dummyButton().text("Build Shop").callback((dummyButton, callbackQuery) -> {
                    Shop shop = shopBuilder.build();
                    if (shop == null) {
                        dummyButton.setText(String.format("%s Shop Creation Failed.", Strings.RED_CIRCLE));
                    } else {
                        dummyButton.setText(String.format("%s Shop Creation Succeeded.", Strings.BLUE_CIRCLE));
                    }
                    // TODO: Register shop
                    selectShop(shop);
                })
                .build()

                .backButton("Back")
                .build();

        return rowBuilder.build().buildMenu();
    }

    private boolean handleShopSelected() {
        if (selectedShop != null) return true;

        // TODO: Send message
        return true;
    }

    private void sendMessage(String format, String... args) {
        String message = String.format(format, args);
    }

}
