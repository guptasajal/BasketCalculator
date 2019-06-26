package com.assignment.shopping;

import com.assignment.shopping.discount.DiscountStrategy;

import java.math.BigDecimal;
import java.util.List;

public class ShoppingCheckout {

    public BigDecimal calculateShoppingTotal(ShoppingBasket basket, List<DiscountStrategy> discountStrategies) {
        // Get the total amount in basket without discount
        BigDecimal totalCost = basket.getItems().stream()
                .map(item -> item.getPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Apply the discounts on the items in basket
        BigDecimal totalDiscount = discountStrategies.stream()
                .map(discountStrategy -> discountStrategy.discountAmount(basket.getItems()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return totalCost.subtract(totalDiscount);
    }
}
