package com.assignment.shopping;

import com.assignment.shopping.entity.Item;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShoppingBasket {

    private final String buyer;

    private final List<Item> items = new ArrayList<Item>();

    public ShoppingBasket(String buyer) {
        this.buyer = buyer;
    }

    public boolean addItem(Item item) {
        return items.add(item);
    }

    public String getBuyer() {
        return buyer;
    }

    public List<Item> getItems() {
        return Collections.unmodifiableList(items);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ShoppingBasket that = (ShoppingBasket) o;
        return buyer.equals(that.buyer);
    }

    @Override
    public int hashCode() {
        return buyer.hashCode();
    }
}