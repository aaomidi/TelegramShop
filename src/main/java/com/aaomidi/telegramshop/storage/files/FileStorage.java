package com.aaomidi.telegramshop.storage.files;

import com.aaomidi.telegramshop.TelegramShop;
import com.aaomidi.telegramshop.storage.ShopStorage;
import com.aaomidi.telegramshop.storage.UserStorage;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

@RequiredArgsConstructor
public class FileStorage {
    private final static transient Gson gson = new Gson();
    private final TelegramShop instance;
    private File dataDir = new File("./data/");
    private File shopFile = new File(dataDir, "Shops.json");
    private File userFile = new File(dataDir, "Users.json");

    public void readFiles() {
        try {
            if (!dataDir.exists()) {
                dataDir.mkdir();
            }

            handleUserFile().prep();
            handleShopFile().prep();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void saveFiles() {
        try {
            {
                UserFile file = UserStorage.genFile();
                String json = gson.toJson(file, UserFile.class);
                writeFile(userFile, json);
            }

            {
                ShopFile file = ShopStorage.genFile();
                String json = gson.toJson(file, ShopFile.class);
                writeFile(shopFile, json);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private UserFile handleUserFile() throws IOException {
        if (!userFile.exists()) {
            userFile.createNewFile();
            UserFile defFile = new UserFile(new ArrayList<>());
            String json = gson.toJson(defFile, UserFile.class);
            writeFile(userFile, json);
            return defFile;
        } else {
            UserFile file = gson.fromJson(new FileReader(userFile), UserFile.class);
            return file;
        }

    }

    private ShopFile handleShopFile() throws IOException {
        if (!shopFile.exists()) {
            shopFile.createNewFile();
            ShopFile defFile = new ShopFile(new ArrayList<>());
            String json = gson.toJson(defFile, ShopFile.class);
            writeFile(userFile, json);
            return defFile;
        } else {
            ShopFile file = gson.fromJson(new FileReader(shopFile), ShopFile.class);
            return file;
        }
    }

    private void writeFile(File file, String json) {
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(json);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
