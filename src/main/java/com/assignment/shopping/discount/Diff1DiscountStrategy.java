package com.assignment.shopping.discount;

import com.assignment.shopping.entity.Item;
import com.assignment.shopping.entity.ItemPrice;

import java.math.BigDecimal;
import java.util.List;

public class Diff1DiscountStrategy implements DiscountStrategy {
    private final String itemName;
    private final BigDecimal basePrice;

    // 3 for 2 --> qtyForDiscount: 3 & qtyToCharge: 2
    private final int qtyForDiscount;
    private final int qtyToCharge;

    public Diff1DiscountStrategy(String itemName, int qtyForDiscount, int qtyToCharge) {
        this.itemName = itemName;
        if(!validQuantity(qtyForDiscount, qtyToCharge)) {
            throw new IllegalArgumentException(String.format("Quantities should be greater than 0. " +
                    "Provided qtyForDiscount: %d, qtyToCharge: %d", qtyForDiscount, qtyToCharge));
        }
        this.qtyForDiscount = qtyForDiscount;
        this.qtyToCharge = qtyToCharge;
        // Assumption: To return Zero price if not present
        this.basePrice = ItemPrice.getItemPrice(itemName).orElse(BigDecimal.ZERO);
    }

    @Override
    public BigDecimal discountAmount(List<Item> items) {
        int count = (int) items.stream().filter(item -> itemName.equals(item.getName())).count();
        int chargeableQty = ((count/qtyForDiscount)*qtyToCharge) + (count%qtyForDiscount);
        int discountQty = count - chargeableQty;

        return basePrice.multiply(BigDecimal.valueOf(discountQty));
    }

    @Override
    public String getItemName() {
        return itemName;
    }

    @Override
    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public int getQtyForDiscount() {
        return qtyForDiscount;
    }

    public int getQtyToCharge() {
        return qtyToCharge;
    }

    private boolean validQuantity(int qtyForDiscount, int qtyToCharge) {
        if(qtyForDiscount <=0 || qtyToCharge <= 0) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DiscountStrategy{" +
                "itemName='" + itemName + '\'' +
                ", qtyForDiscount=" + qtyForDiscount +
                ", basePrice=" + basePrice +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Diff1DiscountStrategy that = (Diff1DiscountStrategy) o;
        if (qtyForDiscount != that.qtyForDiscount) {
            return false;
        }
        if (qtyToCharge != that.qtyToCharge) {
            return false;
        }
        return itemName != null ? itemName.equals(that.itemName) : that.itemName == null;
    }

    @Override
    public int hashCode() {
        int result = itemName != null ? itemName.hashCode() : 0;
        result = 31 * result + qtyForDiscount;
        result = 31 * result + qtyToCharge;
        return result;
    }
}
