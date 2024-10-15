package com.steeka.model;

import java.util.ArrayList;
import java.util.List;

public class RentalCart {

    private List<RentalItem> rentedTools = new ArrayList<>();

    public RentalCart() {
    }

    public void add(RentalItem item) {
        rentedTools.add(item);
    }

    public void remove(RentalItem item) {
        rentedTools.remove(item);
    }

    public void clear() {
        rentedTools.clear();
    }

    public List<RentalItem> getRentedTools() {
        return rentedTools;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RentalCart{");
        sb.append("rentedTools=").append(rentedTools);
        sb.append('}');
        return sb.toString();
    }
}
