package com.assignment.shopping.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

public final class Item implements Serializable {

    private static final long serialVersionUID = 0l;

    private final String itemId;
    private final String name;
    private final BigDecimal price;

    public Item(String name) {
        this.itemId = UUID.randomUUID().toString();
        this.name = name;
        this.price = ItemPrice.getItemPrice(name).orElse(BigDecimal.ZERO);
    }

    public String getItemId() {
        return itemId;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Item item = (Item) o;
        if (!itemId.equals(item.itemId)) {
            return false;
        }
        return name.equals(item.name);

    }

    @Override
    public int hashCode() {
        int result = itemId.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemId='" + itemId + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
