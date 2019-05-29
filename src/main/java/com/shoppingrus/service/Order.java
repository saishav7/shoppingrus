package com.shoppingrus.service;

import com.shoppingrus.model.Item;

import java.math.BigDecimal;

public interface Order {

    Long getTotalQuantity(Item item);

    Long getFreeQuantity(Item item);

    void setFreeQuantity(Item item, Long freeQuantity);

    BigDecimal getFinalUnitPrice(Item item);

    void setDiscountedUnitPrice(Item item, BigDecimal discountedPrice);

    BigDecimal calculateTotal();
}
