package com.shoppingrus.model;

import java.math.BigDecimal;

public class OrderUnit {

    private Item item;
    private long totalQuantity;
    private long freeQuantity;
    //overrides item price
    private BigDecimal finalUnitPrice;

    public OrderUnit(Item item, long totalQuantity) {
        this.item = item;
        this.totalQuantity = totalQuantity;
        this.freeQuantity = 0;
        this.finalUnitPrice = item.getPrice();

    }

    public Item getItem() {
        return item;
    }

    public long getTotalQuantity() {
        return totalQuantity;
    }

    public long getFreeQuantity() {
        return freeQuantity;
    }

    public BigDecimal getFinalUnitPrice() {
        return finalUnitPrice;
    }

    public void setFreeQuantity(long freeQuantity) {
        this.freeQuantity = freeQuantity;
    }

    public void setFinalUnitPrice(BigDecimal finalUnitPrice) {
        this.finalUnitPrice = finalUnitPrice;
    }
}
