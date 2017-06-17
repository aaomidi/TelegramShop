const TelegramBot = require("node-telegram-bot-api");

const config = require("./config/config");

const bot = new TelegramBot(config.token, {polling: true});
