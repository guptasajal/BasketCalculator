package com.assignment.shopping.discount;

import com.assignment.shopping.entity.Item;
import com.assignment.shopping.entity.ItemPrice;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Diff1DiscountStrategyTest {
    private Diff1DiscountStrategy discountStrategy;

    @Before
    public void init() {
        populateItemPrices();

    }

    @Test(expected = IllegalArgumentException.class)
    public void testStrategyCreationWithInvalidQtyValues() {
        discountStrategy = new Diff1DiscountStrategy("Melon", 0, 1);
    }

    @Test
    public void testDiscountAmountWithEmptyList() {
        // Arrange
        discountStrategy = new Diff1DiscountStrategy("Melon", 2, 1);
        // Act
        BigDecimal discountAmount = discountStrategy.discountAmount(Collections.EMPTY_LIST);
        // Assert
        assertEquals(BigDecimal.ZERO, discountAmount);
    }

    @Test
    public void testDiscountAmountWith3For2() {
        // Arrange
        discountStrategy = new Diff1DiscountStrategy("Lime", 3, 2);
        List<Item> itemsList = createItemsList("Apple", "Banana", "Apple", "Lime", "Melon", "Lime",
                "Lime", "Lime", "Melon");
        // Act
        BigDecimal discountAmountInPence = discountStrategy.discountAmount(itemsList);
        // Assert
        assertTrue(discountAmountInPence.compareTo(new BigDecimal("15")) == 0);
    }

    @Test
    public void testDiscountAmountWith4For3() {
        // Arrange
        discountStrategy = new Diff1DiscountStrategy("AnotherFruit", 4, 3);
        List<Item> itemsList = createItemsList("Apple", "Banana", "AnotherFruit", "AnotherFruit", "AnotherFruit", "AnotherFruit");
        // Act
        BigDecimal discountAmountInPence = discountStrategy.discountAmount(itemsList);
        // Assert
        assertTrue(discountAmountInPence.compareTo(new BigDecimal("100")) == 0);
    }

    private List<Item> createItemsList(String... itemNames) {
        List<Item> items = new ArrayList<>();
        Arrays.stream(itemNames).forEach(itemName -> items.add(new Item(itemName)));
        return items;
    }

    private void populateItemPrices() {
        ItemPrice.upsertPrice("Apple", new BigDecimal("35"));
        ItemPrice.upsertPrice("Banana", new BigDecimal("20"));
        ItemPrice.upsertPrice("Melon", new BigDecimal("50"));
        ItemPrice.upsertPrice("Lime", new BigDecimal("15"));
        ItemPrice.upsertPrice("AnotherFruit", new BigDecimal("100"));
    }



}