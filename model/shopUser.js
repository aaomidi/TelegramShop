class ShopUser {
  constructor(telegramId) {
    this.telegramId = telegramId;
    this.currency = 0;
  }

  setCurrency(currency) {
    this.currency = currency;
  }

  getCurrency() {
    return this.currency;
  }

  addCurrency(currency) {
    this.currency += currency;
  }

  removeCurrency(currency) {
    this.currency -= currency;
  }
}

module.exports = { ShopUser };
