package com.steeka.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class RentalAgreement {

    private double total;
    private List<RentalItem> rentedTools;

    public RentalAgreement(List<RentalItem> rentedTools) {
        this.rentedTools = rentedTools;
    }


    public void printRentalAgreement(ToolsRegistry toolsRegistry) {

        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yy");

        rentedTools.forEach(rentalItem -> {

            StringBuilder receipt = new StringBuilder();

            receipt.append("_______________________________________________________________________\n");
            receipt.append(String.format("%-20s %s\n", "Tool code:", rentalItem.getToolCode()));
            Tool tool = toolsRegistry.get(rentalItem.getToolCode());
            receipt.append(String.format("%-20s %s\n", "Tool type:", tool.getType()));
            receipt.append(String.format("%-20s %s\n", "Tool brand:", tool.getBrand()));
            receipt.append(String.format("%-20s %d\n", "Rental days:", rentalItem.getDaysRented()));
            receipt.append(String.format("%-20s %s\n", "Check out date:",
                    formatDate(rentalItem.getCheckoutDate(), dateFormatter)));
            //TODO decide to either recalculate or store the Due Date
            receipt.append(String.format("%-20s %s\n", "Due date:",
                    formatDate(rentalItem.getCheckoutDate().plusDays(rentalItem.getDaysRented()), dateFormatter)));
            receipt.append(String.format("%-20s %s\n", "Daily rental charge:",
                    formatCurrency(tool.getToolCharge().getDailyCharge(), currencyFormatter)));
            receipt.append(String.format("%-20s %s\n", "Pre-discount charge:",
                    formatCurrency(rentalItem.getCharge().totalBeforeDiscounts, currencyFormatter)));
            receipt.append(String.format("%-20s %d%%\n", "Discount percent:", rentalItem.getDiscount()));
            receipt.append(String.format("%-20s %s\n", "Discount amount:",
                    formatCurrency(rentalItem.getCharge().getDiscountedAmount(), currencyFormatter)));
            receipt.append(String.format("%-20s %s\n", "Final charge:",
                    formatCurrency(rentalItem.getCharge().getFinalTotal(), currencyFormatter)));
            receipt.append("_______________________________________________________________________");

            System.out.println(receipt);
        });

    }

    public static String formatCurrency(double amount, NumberFormat currencyFormatter) {
        return currencyFormatter.format(amount);
    }

    public static String formatCurrency(BigDecimal amount, NumberFormat currencyFormatter) {
        return currencyFormatter.format(amount.setScale(2, RoundingMode.HALF_UP));
    }

    public static String formatDate(LocalDate date, DateTimeFormatter dateFormatter) {
        return date.format(dateFormatter);
    }
}
