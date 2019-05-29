package com.shoppingrus.service;

import com.shoppingrus.model.Item;
import com.shoppingrus.rules.BulkDiscountPriceRule;
import com.shoppingrus.rules.BundlePriceRule;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class CheckoutServiceImplTest {

    private CheckoutService checkoutService;

    @Before
    public void setUp() throws Exception {
        checkoutService = new CheckoutServiceImpl(Arrays.asList(
                new BundlePriceRule(Item.MBP, Item.VGA, 1, 1),
                new BundlePriceRule(Item.ATV, 3, 2),
                new BulkDiscountPriceRule(Item.IPD, 4, BigDecimal.valueOf(499.99))
        ));
    }

    @Test
    public void totalWithMultipleItemsWithoutRules() {
        CheckoutService checkoutService = new CheckoutServiceImpl(new ArrayList<>());

        checkoutService.scan(Item.ATV);
        checkoutService.scan(Item.ATV);
        checkoutService.scan(Item.MBP);
        checkoutService.scan(Item.IPD);
        checkoutService.scan(Item.IPD);
        checkoutService.scan(Item.IPD);

        assertEquals(checkoutService.total(), BigDecimal.valueOf(3268.96));
    }

    @Test
    public void totalWithMultipleItemsWithRules1() {
        checkoutService.scan(Item.ATV);
        checkoutService.scan(Item.ATV);
        checkoutService.scan(Item.ATV);
        checkoutService.scan(Item.VGA);

        assertEquals(checkoutService.total(), BigDecimal.valueOf(249.00));
    }

    @Test
    public void totalWithMultipleItemsWithRules2() {
        checkoutService.scan(Item.ATV);
        checkoutService.scan(Item.IPD);
        checkoutService.scan(Item.IPD);
        checkoutService.scan(Item.ATV);
        checkoutService.scan(Item.IPD);
        checkoutService.scan(Item.IPD);
        checkoutService.scan(Item.IPD);

        assertEquals(checkoutService.total(), BigDecimal.valueOf(2718.95));
    }

    @Test
    public void totalWithMultipleItemsWithRules3() {
        checkoutService.scan(Item.MBP);
        checkoutService.scan(Item.VGA);
        checkoutService.scan(Item.IPD);

        assertEquals(checkoutService.total(), BigDecimal.valueOf(1949.98));
    }
}
