package com.steeka.model;

import java.util.Objects;

public class Charge {

    double totalBeforeDiscounts;
    double totalDiscount;
    int daysCharged;
    double finalTotal;

    public Charge() {
        this.daysCharged = 0;
    }

    public int getDaysCharged() {
        return daysCharged;
    }

    public void addOneToDaysCharged() {
        this.daysCharged += 1;
    }

    public void setDaysCharged(int daysCharged) {
        this.daysCharged = daysCharged;
    }

    public double getFinalTotal() {
        return finalTotal;
    }

    public void setFinalTotal(double finalTotal) {
        this.finalTotal = finalTotal;
    }

    public double getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(double totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public double getTotalBeforeDiscounts() {
        return totalBeforeDiscounts;
    }

    public void setTotalBeforeDiscounts(double totalBeforeDiscounts) {
        this.totalBeforeDiscounts = totalBeforeDiscounts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Charge charge = (Charge) o;
        return Double.compare(totalBeforeDiscounts, charge.totalBeforeDiscounts) == 0 && Double.compare(totalDiscount, charge.totalDiscount) == 0 && daysCharged == charge.daysCharged && Double.compare(finalTotal, charge.finalTotal) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(totalBeforeDiscounts, totalDiscount, daysCharged, finalTotal);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Charge{");
        sb.append("totalBeforeDiscounts=").append(totalBeforeDiscounts);
        sb.append(", totalDiscount=").append(totalDiscount);
        sb.append(", daysCharged=").append(daysCharged);
        sb.append(", finalTotal=").append(finalTotal);
        sb.append('}');
        return sb.toString();
    }
}
