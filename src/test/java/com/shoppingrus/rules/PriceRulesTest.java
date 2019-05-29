package com.shoppingrus.rules;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.shoppingrus.model.Item;
import com.shoppingrus.rules.BulkDiscountPriceRule;
import com.shoppingrus.rules.BundlePriceRule;
import com.shoppingrus.rules.PriceRule;
import com.shoppingrus.service.OrderImpl;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;


public class PriceRulesTest {

    private Item ITEM_A;
    private Item ITEM_B;
    private Item ITEM_C;

    private OrderImpl order;

    @Before
    public void setup() {
        order = mock(OrderImpl.class);
        ITEM_A = Item.IPD;
        ITEM_B = Item.MBP;
        ITEM_C = Item.VGA;
    }

    @Test
    public void shouldApplyBulkDiscount() {
        //Reduce price to $499.99 if quantity is 4 or more on ITEM_A
        BigDecimal newPrice = new BigDecimal(499.99);
        PriceRule rule = new BulkDiscountPriceRule(ITEM_A, 4, newPrice);
        when(order.getTotalQuantity(ITEM_A)).thenReturn(5L);

        rule.apply(order);

        verify(order).setDiscountedUnitPrice(ITEM_A, newPrice);
    }

    @Test
    public void shouldNotApplyBulkDiscount() {
        //Reduce price to $499.99 if quantity is 4 or more on ITEM_A
        BigDecimal newPrice = new BigDecimal(499.99);
        PriceRule rule = new BulkDiscountPriceRule(ITEM_A, 4, newPrice);
        when(order.getTotalQuantity(ITEM_A)).thenReturn(3L);

        rule.apply(order);

        verify(order, times(0)).setDiscountedUnitPrice(ITEM_A, newPrice);
    }


    @Test
    public void shouldAddFreeItem() {
        //Bundle ITEM_C for free with every ITEM_B
        PriceRule rule = new BundlePriceRule(ITEM_B, ITEM_C, 1, 1);
        when(order.getTotalQuantity(ITEM_B)).thenReturn(1L);

        rule.apply(order);

        verify(order).setFreeQuantity(ITEM_C, 1L);
    }

    @Test
    public void shouldApplyQuantityDeal() {
        //3 for 2 deal on ITEM_A
        PriceRule rule = new BundlePriceRule(ITEM_A, 3, 2);
        when(order.getTotalQuantity(ITEM_A)).thenReturn(6L);

        rule.apply(order);

        verify(order).setFreeQuantity(ITEM_A, 2L);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenQuantityNotValid() {
        //3 for 2 deal on ITEM_A
        PriceRule rule = new BundlePriceRule(ITEM_A, 3, 4);
        when(order.getTotalQuantity(ITEM_A)).thenReturn(6L);

        rule.apply(order);
    }

}
