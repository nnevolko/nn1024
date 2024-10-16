package com.steeka.model;

import com.steeka.utility.Formatter;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.Normalizer;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


public class RentalAgreementDTO {

    private String toolCode;
    private String type;
    private String brand;

    private BigDecimal dailyCharge;

    private LocalDate checkoutDate;
    private LocalDate returnDate;
    private int discount;
    private int daysRented;

    BigDecimal totalBeforeDiscounts;
    BigDecimal discountedAmount;
    BigDecimal finalTotal;
    int daysCharged;

    public RentalAgreementDTO(@NotNull RentalItem rentalItem, @NotNull ToolsRegistry toolRegistry) {
        Tool tool =  toolRegistry.getToolByCode(rentalItem.getToolCode());
        Charge charge = rentalItem.getCharge();

        setToolCode(tool.getCode());
        setType(tool.getType());
        setBrand(tool.getBrand());
        setDailyCharge(tool.getToolCharge().getDailyCharge());

        setCheckoutDate(rentalItem.getCheckoutDate());
        setReturnDate(rentalItem.getCheckoutDate().plusDays(rentalItem.getDaysRented()));
        setDiscount(rentalItem.getDiscount());
        setDaysRented(rentalItem.getDaysRented());

        setTotalBeforeDiscounts(charge.getTotalBeforeDiscounts());
        setDiscountedAmount(charge.getDiscountedAmount());
        setFinalTotal(charge.getFinalTotal());
        setDaysCharged(charge.getDaysCharged());

    }

    public String getToolCode() {
        return toolCode;
    }

    public void setToolCode(String toolCode) {
        this.toolCode = toolCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public BigDecimal getDailyCharge() {
        return dailyCharge;
    }

    public void setDailyCharge(BigDecimal dailyCharge) {
        this.dailyCharge = dailyCharge;
    }

    public LocalDate getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(LocalDate checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getDaysRented() {
        return daysRented;
    }

    public void setDaysRented(int daysRented) {
        this.daysRented = daysRented;
    }

    public BigDecimal getTotalBeforeDiscounts() {
        return totalBeforeDiscounts;
    }

    public void setTotalBeforeDiscounts(BigDecimal totalBeforeDiscounts) {
        this.totalBeforeDiscounts = totalBeforeDiscounts;
    }

    public BigDecimal getDiscountedAmount() {
        return discountedAmount;
    }

    public void setDiscountedAmount(BigDecimal discountedAmount) {
        this.discountedAmount = discountedAmount;
    }

    public BigDecimal getFinalTotal() {
        return finalTotal;
    }

    public void setFinalTotal(BigDecimal finalTotal) {
        this.finalTotal = finalTotal;
    }

    public int getDaysCharged() {
        return daysCharged;
    }

    public void setDaysCharged(int daysCharged) {
        this.daysCharged = daysCharged;
    }

    public void printRentalAgreement() {
            System.out.println(this);
    }

    @Override
    public String toString() {
        final StringBuilder sb = buildItemReceipt();
        return sb.toString();
    }

    public StringBuilder buildItemReceipt(){
        StringBuilder receipt = new StringBuilder();

        receipt.append(String.format("%-20s %s\n", "Tool code:", this.toolCode));
        receipt.append(String.format("%-20s %s\n", "Tool type:", this.type));
        receipt.append(String.format("%-20s %s\n", "Tool brand:", this.brand));
        receipt.append(String.format("%-20s %d\n", "Rental days:", this.daysRented));
        receipt.append(String.format("%-20s %s\n", "Check out date:", formatDate(checkoutDate, Formatter.DATE_CONVERTER)));
        receipt.append(String.format("%-20s %s\n", "Due date:", formatDate(this.returnDate, Formatter.DATE_CONVERTER)));
        receipt.append(String.format("%-20s %s\n", "Daily rental charge:", formatCurrency(this.dailyCharge, Formatter.CURRENCY_CONVERTER )));
        receipt.append(String.format("%-20s %s\n", "Pre-discount charge:", formatCurrency(this.totalBeforeDiscounts, Formatter.CURRENCY_CONVERTER)));
        receipt.append(String.format("%-20s %d%%\n", "Discount percent:", this.discount));
        receipt.append(String.format("%-20s %s\n", "Discount amount:", formatCurrency(this.discountedAmount, Formatter.CURRENCY_CONVERTER)));
        receipt.append(String.format("%-20s %s\n", "Final charge:", formatCurrency(this.finalTotal, Formatter.CURRENCY_CONVERTER)));
        return receipt;
    }

    public static String formatCurrency(@NotNull BigDecimal amount, @NotNull NumberFormat currencyFormatter) {
        return currencyFormatter.format(amount.setScale(2, RoundingMode.HALF_UP));
    }

    public static String formatDate(@NotNull LocalDate date, @NotNull DateTimeFormatter dateFormatter) {
        return date.format(dateFormatter);
    }
}
