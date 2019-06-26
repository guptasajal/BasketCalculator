package com.assignment.shopping.discount;

import com.assignment.shopping.entity.Item;

import java.math.BigDecimal;
import java.util.List;

public interface DiscountStrategy {
    BigDecimal discountAmount(List<Item> items);

    String getItemName();

    BigDecimal getBasePrice();
}
