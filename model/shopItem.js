class ShopItem {
    constructor(shop, name, description, duration, cost) {
        this.shop = shop;
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.cost = cost;
    }

    getShop() {
        return this.shop;
    }

    getName() {
        return this.name;
    }

    setName(name) {
        this.name = name;
    }

    getDescription() {
        return this.description;
    }

    setDescription(description) {
        this.description = description;
    }

    getDuration() {
        return this.duration;
    }

    setDuration(duration) {
        this.duration = duration;
    }

    getCost() {
        return this.cost;
    }

    setCost(cost) {
        this.cost = cost;
    }
}