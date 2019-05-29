package com.shoppingrus.service;

import com.shoppingrus.model.Item;

import java.math.BigDecimal;

public interface CheckoutService {

    void scan(Item item);

    BigDecimal total();
}
