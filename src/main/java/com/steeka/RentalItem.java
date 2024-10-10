package com.steeka;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
/*
Requirement:
Tool code - See tool table above
Rental day count - The number of days for which the customer wants to rent the tool. (e.g. 4
days)
Discount percent - As a whole number, 0-100 (e.g. 20 = 20%)
Check out date

Exception handling:
Checkout should throw an exception with an instructive, user-friendly message if
Rental day count is not 1 or greater
Discount percent is not in the range 0-100
 */
public class RentalItem {

    private static final DateTimeFormatter inputDateFormatter = DateTimeFormatter.ofPattern("M/d/yy");

    private String toolCode;
    private LocalDate checkoutDate;
    private int discount;
    private int daysRented;

    public RentalItem(String toolCode,  String checkoutDate, String daysRented, String discount) {
        this.toolCode = toolCode.trim();
        this.checkoutDate = LocalDate.parse(checkoutDate.trim(), inputDateFormatter);;
        this.daysRented = Integer.parseInt(daysRented.trim());
        this.discount = Integer.parseInt(discount.replace("%", ""));

        //TODO check this. Whole numbers
        if (this.discount>100) {
            throw new IllegalArgumentException("Rental Discount cannot be greater than 100 percent. Please check with the Clerk");
        }

        if (this.daysRented<1) {
            throw new IllegalArgumentException("Tools can be rented for 1 or more days only. Please adjust it.");
        }
    }

    public String getToolCode() {
        return toolCode;
    }

    public void setToolCode(String toolCode) {
        this.toolCode = toolCode;
    }

    public LocalDate getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(LocalDate checkoutDate) {
        this.checkoutDate = checkoutDate;
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
}
