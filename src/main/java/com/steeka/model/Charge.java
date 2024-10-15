package com.steeka.model;

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
