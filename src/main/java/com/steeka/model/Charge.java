package com.steeka.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Charge {

    BigDecimal totalBeforeDiscounts;
    BigDecimal discountedAmount;
    int daysCharged;
    BigDecimal finalTotal;

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

    public BigDecimal getFinalTotal() {
        return finalTotal;
    }

    public void setFinalTotal(BigDecimal finalTotal) {
        this.finalTotal = finalTotal;
    }

    public BigDecimal getDiscountedAmount() {
        return discountedAmount;
    }

    public void setDiscountedAmount(BigDecimal discountedAmount) {
        this.discountedAmount = discountedAmount;
    }

    public BigDecimal getTotalBeforeDiscounts() {
        return totalBeforeDiscounts;
    }

    public void setTotalBeforeDiscounts(BigDecimal totalBeforeDiscounts) {
        this.totalBeforeDiscounts = totalBeforeDiscounts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Charge charge = (Charge) o;
        return daysCharged == charge.daysCharged && Objects.equals(totalBeforeDiscounts, charge.totalBeforeDiscounts) && Objects.equals(discountedAmount, charge.discountedAmount) && Objects.equals(finalTotal, charge.finalTotal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(totalBeforeDiscounts, discountedAmount, daysCharged, finalTotal);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Charge{");
        sb.append("totalBeforeDiscounts=").append(totalBeforeDiscounts);
        sb.append(", discountedAmount=").append(discountedAmount);
        sb.append(", daysCharged=").append(daysCharged);
        sb.append(", finalTotal=").append(finalTotal);
        sb.append('}');
        return sb.toString();
    }
}
