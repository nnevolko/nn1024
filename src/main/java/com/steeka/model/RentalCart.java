package com.steeka.model;

import java.util.ArrayList;
import java.util.List;

public class RentalCart {

    //TODO ideally this method would contain a list of rental items
    // but for current requirements I refactored to include only one item.
    private RentalItem rentalItem;

    public RentalCart(RentalItem rentalItem) {
        this.rentalItem = rentalItem;
    }

    public RentalItem getRentalItem() {
        return rentalItem;
    }

    public void setRentalItem(RentalItem rentalItem) {
        this.rentalItem = rentalItem;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RentalCart{");
        sb.append("rentalItem=").append(rentalItem);
        sb.append('}');
        return sb.toString();
    }
}
