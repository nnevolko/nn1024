package com.steeka;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class RentalAgreement {

    private double total;
    private List<RentalItem> rentedTools;
    private LocalDate checkoutDate;

    public RentalAgreement(LocalDate checkoutDate, List<RentalItem> rentedTools) {
        this.checkoutDate = checkoutDate;
        this.rentedTools = new ArrayList<>();
    }

    public void generateRentalAggreement( ){
        /*
         Tool code: LADW
         Tool type: Ladder
         Tool brand:
         Rental days:
         Check out date:
         Due date:
         Daily rental chrae:
         Pre-discount charge
         Disocunt percent:
         Discount amount:
         Final charge: $9.99

         formatting as follows:
         Date mm/dd/yy
         Currency: $9.999.99
         Percent 99%
         */

        String formattedDate = checkoutDate.format(DateTimeFormatter.ISO_DATE);

        rentedTools.forEach(tool -> {
            System.out.printf("%-20s %s\n", "Tool code:", tool.getTool().getCode());
            System.out.printf("%-20s %s\n", "Tool type:", tool.getTool().getType());
            System.out.printf("%-20s %s\n", "Tool brand:", tool.getTool().getBrand());
            System.out.printf("%-20s %s\n", "Rental days:", tool.getDaysRented());
            System.out.printf("%-20s %s\n", "Check out date:", formattedDate);
        });

    }
}
