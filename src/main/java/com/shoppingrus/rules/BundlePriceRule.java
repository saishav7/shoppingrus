package com.shoppingrus.rules;

import com.shoppingrus.model.Item;
import com.shoppingrus.service.Order;

public class BundlePriceRule implements PriceRule {

    private final Item mainItem;
    private final Item bundledItem;
    private final long mainItemQuantity;
    private final long bundledItemQuantity;

    //For bundling 1 MBP with 1VGA etc
    public BundlePriceRule(Item mainItem, Item bundledItem, long mainItemQuantity, long bundledItemQuantity) {
        this.mainItem = mainItem;
        this.bundledItem = bundledItem;
        this.mainItemQuantity = mainItemQuantity;
        this.bundledItemQuantity = bundledItemQuantity;
    }

    //For cases like 3 for 2 etc
    public BundlePriceRule(Item item, long offerQuantity, long originalQuantity) {
        if (offerQuantity <= originalQuantity) {
            throw new IllegalArgumentException("Offer quantity must be greater than original");
        }
        this.mainItem = item;
        this.bundledItem = item;
        this.mainItemQuantity = offerQuantity;
        this.bundledItemQuantity = offerQuantity - originalQuantity;
    }

    @Override
    public Order apply(Order order) {
        Long itemsPurchased = order.getTotalQuantity(mainItem);
        if (itemsPurchased >= mainItemQuantity) {
            long ruleInvocationCount = itemsPurchased / mainItemQuantity;
            order.setFreeQuantity(bundledItem, order.getFreeQuantity(bundledItem)
                    + (ruleInvocationCount * bundledItemQuantity));
        }
        return order;
    }
}
