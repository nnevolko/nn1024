package com.steeka;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

public class Charge {

    public static final Locale US_LOCALE = Locale.US;
    private BigDecimal amount;
    private Currency currencyType;

    public Charge(BigDecimal amount, Currency currencyType) {
        this.amount = amount;
        this.currencyType = currencyType;

        /*
        BigDecimal total = amount.add(new BigDecimal("20.25"));
   BigDecimal discounted = amount.multiply(new BigDecimal("0.9"));
         */
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        //refactor this
        //this code needs to go elsewhere also need to allow for other currencies
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(US_LOCALE);
        String formattedAmount = currencyFormatter.format(amount);

        return currencyType.getSymbol() + formattedAmount;
    }
}
