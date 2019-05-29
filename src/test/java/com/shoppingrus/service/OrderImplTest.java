package com.shoppingrus.service;

import com.shoppingrus.model.Item;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class OrderImplTest {

    private Order order;

    @Test
    public void setFreeQuantity() {
        order =  new OrderImpl(Arrays.asList(Item.VGA, Item.ATV, Item.ATV, Item.ATV));

        order.setFreeQuantity(Item.ATV, 1L);

        assertEquals((long) order.getFreeQuantity(Item.ATV), 1L);
    }

    @Test
    public void setDiscountedUnitPrice() {
        BigDecimal newPrice = BigDecimal.valueOf(99.99);
        order =  new OrderImpl(Arrays.asList(Item.VGA, Item.ATV));

        order.setDiscountedUnitPrice(Item.ATV, newPrice);

        assertEquals(order.getFinalUnitPrice(Item.ATV), newPrice);
    }

    @Test
    public void calculateTotal() {
        order =  new OrderImpl(Arrays.asList(Item.VGA, Item.ATV, Item.ATV, Item.ATV));
        assertEquals(order.calculateTotal(), BigDecimal.valueOf(358.5));
    }

    @Test
    public void calculateTotalWhenOrderIsEmpty() {
        order =  new OrderImpl(new ArrayList<>());
        assertEquals(order.calculateTotal(), BigDecimal.ZERO);
    }

}
