package com.assignment.shopping;

import com.assignment.shopping.discount.Diff1DiscountStrategy;
import com.assignment.shopping.discount.DiscountStrategy;
import com.assignment.shopping.entity.Item;
import com.assignment.shopping.entity.ItemPrice;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class ShoppingCheckoutTest {

    private ShoppingCheckout shoppingCheckout;

    @Before
    public void init() {
        shoppingCheckout = new ShoppingCheckout();
        populateItemPrices();
    }

    @Test
    public void testEmptyBasketTotalCostIsZero() {
        // Arrange
        ShoppingBasket basket = new ShoppingBasket("user");
        // Act
        BigDecimal totalInPence = shoppingCheckout.calculateShoppingTotal(basket, Collections.emptyList());
        // Assert
        assertEquals(BigDecimal.ZERO, totalInPence);
    }

    @Test
    public void testBasketWithNoDiscountStrategy() {
        // Arrange
        ShoppingBasket basket = createBasket("user", "Apple", "Banana", "Apple", "Lime");
        // Act
        BigDecimal totalInPence = shoppingCheckout.calculateShoppingTotal(basket, Collections.emptyList());
        // Assert
        assertNotEquals(BigDecimal.ZERO, totalInPence);
        assertTrue(totalInPence.compareTo(new BigDecimal("105")) == 0);
    }

    @Test
    public void testBasketWithOneDiscountStrategyWhichIsApplied() {
        // Arrange
        ShoppingBasket basket = createBasket("user","Banana", "Apple", "Lime", "Melon", "Melon", "Melon");
        List<DiscountStrategy> discountStrategies = Arrays.asList(new Diff1DiscountStrategy("Melon", 2, 1));
        // Act
        BigDecimal totalInPence = shoppingCheckout.calculateShoppingTotal(basket, discountStrategies);
        // Assert
        assertNotEquals(BigDecimal.ZERO, totalInPence);
        assertTrue(totalInPence.compareTo(new BigDecimal("170")) == 0);
    }

    @Test
    public void testBasketWithTwoDiscountStrategyButOnlyOneIsApplied() {
        // Arrange
        ShoppingBasket basket = createBasket("user", "Apple", "Banana", "Apple", "Lime", "Melon", "Lime", "Lime");
        List<DiscountStrategy> discountStrategies = Arrays.asList(
                new Diff1DiscountStrategy("Melon", 2, 1),
                new Diff1DiscountStrategy("Lime", 3, 2));
        // Act
        BigDecimal totalInPence = shoppingCheckout.calculateShoppingTotal(basket, discountStrategies);
        // Assert
        assertNotEquals(BigDecimal.ZERO, totalInPence);
        assertTrue(totalInPence.compareTo(new BigDecimal("170")) == 0);
    }

    @Test
    public void testBasketWithTwoDiscountStrategyAndBothAreEligible() {
        // Arrange
        ShoppingBasket basket = createBasket("user", "Apple", "Banana", "Apple", "Lime", "Melon", "Lime",
                "Lime", "Lime", "Melon");
        List<DiscountStrategy> discountStrategies = Arrays.asList(
                new Diff1DiscountStrategy("Melon", 2, 1),
                new Diff1DiscountStrategy("Lime", 3, 2));
        // Act
        BigDecimal totalInPence = shoppingCheckout.calculateShoppingTotal(basket, discountStrategies);
        // Assert
        assertNotEquals(BigDecimal.ZERO, totalInPence);
        assertTrue(totalInPence.compareTo(new BigDecimal("185")) == 0);
    }

    private ShoppingBasket createBasket(String user, String... itemNames) {
        ShoppingBasket basket = new ShoppingBasket(user);
        Arrays.stream(itemNames).forEach(itemName -> basket.addItem(new Item(itemName)));
        return basket;
    }

    private void populateItemPrices() {
        ItemPrice.upsertPrice("Apple", new BigDecimal("35"));
        ItemPrice.upsertPrice("Banana", new BigDecimal("20"));
        ItemPrice.upsertPrice("Melon", new BigDecimal("50"));
        ItemPrice.upsertPrice("Lime", new BigDecimal("15"));
    }
}