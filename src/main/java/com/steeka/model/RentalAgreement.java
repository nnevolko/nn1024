package com.steeka.model;

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
            System.out.println("_______________________________________________________________________");
            System.out.printf("%-20s %s\n", "Tool code:", rentalItem.getToolCode());
            Tool tool = toolsRegistry.get(rentalItem.getToolCode());
            System.out.printf("%-20s %s\n", "Tool type:", tool.getType());
            System.out.printf("%-20s %s\n", "Tool brand:", tool.getBrand());
            System.out.printf("%-20s %d\n", "Rental days:", rentalItem.getDaysRented());
            System.out.printf("%-20s %s\n", "Check out date:",
                    formatDate(rentalItem.getCheckoutDate(), dateFormatter));
            //TODO decide to either recalculate or store the Due Date
            System.out.printf("%-20s %s\n", "Due date:",
                    formatDate(rentalItem.getCheckoutDate().plusDays(rentalItem.getDaysRented()), dateFormatter));
            System.out.printf("%-20s %s\n", "Daily rental charge:",
                                 formatCurrency(tool.getToolCharge().getDailyCharge(), currencyFormatter));
            System.out.printf("%-20s %s\n", "Pre-discount charge:",
                                 formatCurrency(rentalItem.getCharge().totalBeforeDiscounts, currencyFormatter));
            System.out.printf("%-20s %d%%\n", "Discount percent:", rentalItem.getDiscount());
            System.out.printf("%-20s %s\n", "Discount amount:",
                                formatCurrency(rentalItem.getCharge().getTotalDiscount(), currencyFormatter));
            System.out.printf("%-20s %s\n", "Final charge:",
                              formatCurrency(rentalItem.getCharge().getFinalTotal(), currencyFormatter));
            System.out.println("_______________________________________________________________________");
        });

    }

    public static String formatCurrency(double amount, NumberFormat currencyFormatter) {
        return currencyFormatter.format(amount);
    }

    public static String formatDate(LocalDate date, DateTimeFormatter dateFormatter) {
        return date.format(dateFormatter);
    }
}
