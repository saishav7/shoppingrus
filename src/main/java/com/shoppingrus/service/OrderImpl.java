package com.shoppingrus.service;

import com.shoppingrus.model.Item;
import com.shoppingrus.model.OrderUnit;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class OrderImpl implements Order {
    private final static Logger LOGGER = Logger.getLogger(OrderImpl.class.getName());

    //Using map for easier lookups
    private Map<String, OrderUnit> order;

    OrderImpl(List<Item> items) {
        Map<Item, Long> itemsByCount = items.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        order = itemsByCount.entrySet().stream().collect(HashMap::new, (map, unit) -> {
            Item item = unit.getKey();
            Long quantity = unit.getValue();
            map.put(item.getSku(), new OrderUnit(item, quantity));
        }, HashMap::putAll);
    }

    @Override
    public void setFreeQuantity(Item item, Long freeQuantity) {
        if (!order.containsKey(item.getSku())) {
            order.put(item.getSku(), new OrderUnit(item, freeQuantity));
        }
        order.get(item.getSku()).setFreeQuantity(freeQuantity);
        LOGGER.log(Level.INFO, String.format("Set free quantity: %s for item: %s",freeQuantity, item.getSku()));
    }

    @Override
    public void setDiscountedUnitPrice(Item item, BigDecimal discountedPrice) {
        checkItemExistsInOrder(item);
        order.get(item.getSku()).setFinalUnitPrice(discountedPrice);
        LOGGER.log(Level.INFO, String.format("Set discounted price: %s for item: %s",discountedPrice, item.getSku()));
    }

    @Override
    public Long getTotalQuantity(Item item) {
        if (order.containsKey(item.getSku())) {
            return order.get(item.getSku()).getTotalQuantity();
        }
        return 0L;
    }

    @Override
    public Long getFreeQuantity(Item item) {
        if (order.containsKey(item.getSku())) {
            return order.get(item.getSku()).getFreeQuantity();
        }
        return 0L;
    }

    @Override
    public BigDecimal getFinalUnitPrice(Item item) {
        if (order.containsKey(item.getSku())) {
            return order.get(item.getSku()).getFinalUnitPrice();
        }
        return BigDecimal.ZERO;
    }

    private void checkItemExistsInOrder(Item item) {
        if (!order.containsKey(item.getSku())) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public BigDecimal calculateTotal() {
        return order.values().stream()
                .map(e -> e.getFinalUnitPrice()
                        .multiply(BigDecimal.valueOf(e.getTotalQuantity() - e.getFreeQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
