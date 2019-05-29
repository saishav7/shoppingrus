package com.shoppingrus.rules;

import com.shoppingrus.service.Order;

public interface PriceRule {

    Order apply(Order order);
}
