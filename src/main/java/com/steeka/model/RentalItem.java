package com.steeka.model;

import com.steeka.exception.ToolsRentalArgumentException;

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
    private int daysRented;
    private int discount;

    Charge charge;

    public RentalItem(String toolCode, String checkoutDate, String daysRented, String discount) {
        this.toolCode = toolCode.trim();
        this.checkoutDate = LocalDate.parse(checkoutDate.trim(), inputDateFormatter);
        this.daysRented = Integer.parseInt(daysRented.trim());
        this.discount = Integer.parseInt(discount.replace("%", ""));

        //TODO check this. Whole numbers
        if (this.discount < 0 || this.discount > 100) {
            throw new ToolsRentalArgumentException("Rental discount cannot be greater than 100%. Please check with the Clerk.");
        }

        if (this.daysRented < 1) {
            throw new ToolsRentalArgumentException("Tools can be rented for 1 or more days. Please adjust selected number of days.");
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

    public Charge getCharge() {
        return charge;
    }

    public void setCharge(Charge charge) {
        this.charge = charge;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RentalItem{");
        sb.append("toolCode='").append(toolCode).append('\'');
        sb.append(", checkoutDate=").append(checkoutDate);
        sb.append(", discount=").append(discount);
        sb.append(", daysRented=").append(daysRented);
        sb.append(", charge=").append(charge);
        sb.append('}');
        return sb.toString();
    }
}
