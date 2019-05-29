package com.shoppingrus.service;

import com.shoppingrus.model.Item;
import com.shoppingrus.rules.PriceRule;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CheckoutServiceImpl implements CheckoutService {

    private final List<PriceRule> rules;
    private final List<Item> items;

    private final static Logger LOGGER = Logger.getLogger(CheckoutServiceImpl.class.getName());

    public CheckoutServiceImpl(List<PriceRule> rules) {
        this.rules = rules;
        items = new ArrayList<>();
    }

    @Override
    public void scan(Item item) {
        items.add(item);
        LOGGER.log(Level.INFO, "Item scanned: {0}", item.getSku());
    }

    @Override
    public BigDecimal total() {
        Order order = new OrderImpl(items);

        for (PriceRule rule : rules) {
            order = rule.apply(order);
        }

        BigDecimal total = order.calculateTotal();
        LOGGER.log(Level.INFO, "Total amount: {0}", total);
        return total;
    }
}
