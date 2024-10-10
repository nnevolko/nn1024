package com.steeka;

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


    public void printRentalAgreement() {
        /*
         Tool code: LADW
         Tool type: Ladder
         Tool brand:
         Rental days:
         Check out date:
         Due date:
         Daily rental chrae:
         Pre-discount charge
         Discount percent:
         Discount amount:
         Final charge: $9.99

         formatting as follows:
         Date mm/dd/yy
         Currency: $9.999.99
         Percent 99%
         */
        //TODO take these settings to a separate file so they could be easily changed or modified
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yy");

        rentedTools.forEach(rentalItem -> {
            System.out.printf("%-20s %s\n", "Tool code:", rentalItem.getToolCode());
            Tool tool = ToolsRegistry.getToolByCode(rentalItem.getToolCode());
            System.out.printf("%-20s %s\n", "Tool type:", tool.getType());
            System.out.printf("%-20s %s\n", "Tool brand:", tool.getBrand());
            System.out.printf("%-20s %d\n", "Rental days:", rentalItem.getDaysRented());
            System.out.printf("%-20s %s\n", "Check out date:", formatDate(rentalItem.getCheckoutDate(), dateFormatter));
            System.out.printf("%-20s %s\n", "Due date:", "formatDate(dueDate, dateFormatter)");
            System.out.printf("%-20s %s\n", "Daily rental charge:", "Dailyrentalcharge");
            System.out.printf("%-20s %s\n", "Pre-discount charge:", "formatCurrency(preDiscountCharge, currencyFormatter)");
            System.out.printf("%-20s %d%%\n", "Discount percent:", rentalItem.getDiscount());
            System.out.printf("%-20s %s\n", "Discount amount:", "currencyFormatter.format(discountAmount)");
            System.out.printf("%-20s %s\n", "Final charge:", "currencyFormatter.format(finalCharge)");

        });

    }

    public static String formatCurrency(double amount, NumberFormat currencyFormatter) {
        return currencyFormatter.format(amount);
    }

    public static String formatDate(LocalDate date, DateTimeFormatter dateFormatter) {
        return date.format(dateFormatter);
    }
}
