package com.aaomidi.telegramshop.listner;

import com.aaomidi.telegramshop.TelegramShop;
import pro.zackpollard.telegrambot.api.event.Listener;
import pro.zackpollard.telegrambot.api.event.chat.message.TextMessageReceivedEvent;

public class MessageListener implements Listener {
    private final TelegramShop instance;

    public MessageListener(TelegramShop instance) {
        this.instance = instance;
    }

    @Override
    public void onTextMessageReceived(TextMessageReceivedEvent event) {
    }
}
