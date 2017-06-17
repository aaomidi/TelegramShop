class Shop {
  constructor() {
    this.currencySymbol = "$";
      this.currencyName = "USD";

    this.shopName = "Shop";
    this.shopItems = [];
    this.shopUsers = [];
  }

  setCurrencySymbol(currencySymbol) {
    this.currencySymbol = currencySymbol;
  }

  getCurrencySymbol() {
    return this.currencySymbol;
  }

  setCurrencyName(currencyName) {
    this.currencyName = currencyName;
  }

  getCurrencyName() {
    return this.currencyName;
  }

  setShopName(shopName) {
    this.shopName = shopName;
  }

  getShopName() {
    return this.shopName;
  }

  addShopItem(shopItem) {
    this.shopItems.push(shopItem);
  }

  removeShopItem(shopItem) {
    this.shopItems.splice(this.shopItems.indexOf(shopItem), 1);
  }

  getShopItems() {
    return this.shopItems.slice(0);
  }

  addShopUser(shopUser) {
    this.shopUsers.push(shopUser);
  }

  removeShopUser(shopUser) {
    this.shopUsers.splice(this.shopUsers.indexOf(shopUser), 1);
  }

  getShopUsers() {
    return this.shopUsers.slice(0);
  }
}

module.exports = { Shop };
