package com.shoppingrus.model;

import java.math.BigDecimal;
import java.util.Arrays;

/**
 * Using hardcoded items, ideally this would be persisted on a db
 */
public enum Item {

    IPD("ipd", "Super iPad", 549.99),
    MBP("mpb", "MacBook Pro", 1399.99),
    ATV("atv", "Apple TV", 109.50),
    VGA("vga", "VGA adapter", 30.00);

    private String sku;
    private String name;
    private BigDecimal price;

    Item(String sku, String name, double price) {
        this.sku = sku;
        this.name = name;
        this.price = BigDecimal.valueOf(price);
    }

    public static Item findBySku(String sku) {
        return Arrays.stream(values()).filter(value -> value.getSku().equals(sku))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Item does not exist"));
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    
}
