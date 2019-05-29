package com.shoppingrus.rules;

import com.shoppingrus.model.Item;
import com.shoppingrus.service.Order;

import java.math.BigDecimal;

public class BulkDiscountPriceRule implements PriceRule {

    private final Item item;
    private final long quantity;
    private final BigDecimal priceAfterDiscount;

    public BulkDiscountPriceRule(Item item, long quantity, BigDecimal priceAfterDiscount) {
        this.item = item;
        this.quantity = quantity;
        this.priceAfterDiscount = priceAfterDiscount;
    }

    @Override
    public Order apply(Order order) {
        Long totalQuantity = order.getTotalQuantity(item);

        if (totalQuantity > quantity) {
            order.setDiscountedUnitPrice(item, priceAfterDiscount);
        }
        return order;
    }
}
