package com.assignment.shopping.entity;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class ItemPrice {
    private static final Map<String, BigDecimal> ITEM_PRICES = new ConcurrentHashMap<>();

    public static void upsertPrice(String itemName, BigDecimal price) {
        ITEM_PRICES.put(itemName, price);
    }

    public static Optional<BigDecimal> getItemPrice(String item) {
        return Optional.ofNullable(ITEM_PRICES.get(item));
    }
}
