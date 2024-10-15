package com.steeka.processor;

import com.steeka.exception.ToolsRentalException;
import com.steeka.model.Charge;
import com.steeka.model.RentalItem;
import com.steeka.model.Tool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

/**
 * Functional Interface to calculate charges for a given tool
 * Take into account
 * weekday, weekend and holiday charges
 */

public class ChargesCalculator {

    private static final Logger logger = LoggerFactory.getLogger(ChargesCalculator.class);

    public static void calculate(RentalItem rentalItem, Tool tool) {

        if (rentalItem.getDaysRented() <= 1) {
            throw new ToolsRentalException("Rental must be for 1 or more days.");
        }
        Charge newCharge = new Charge();

        LocalDate checkoutDate = rentalItem.getCheckoutDate();
        LocalDate returnDate = rentalItem.getCheckoutDate().plusDays(rentalItem.getDaysRented());

        //TODO Check here ->>
        // Charge days - Count of chargeable days, from day after checkout through and including due
        //date, excluding “no charge” days as specified by the tool type.
        for (LocalDate date = checkoutDate.plusDays(1); date.isBefore(returnDate); date.plusDays(1)) {
            //check if weekend
            if (isWeekend(date) && tool.getToolCharge().isHasWeekendCharge()) {
                newCharge.addOneToDaysCharged();
            } else if (isHoliday(date) && tool.getToolCharge().isHasHolidayCharge()) {
                newCharge.addOneToDaysCharged();
            } else
                newCharge.addOneToDaysCharged();
        }

        logger.info("Total charged days: " + newCharge.getDaysCharged());

        newCharge.setTotalBeforeDiscounts(newCharge.getDaysCharged() * tool.getToolCharge().getDailyCharge());
        logger.info("Pre-discount charge: " + newCharge.getTotalBeforeDiscounts());

        newCharge.setTotalDiscount(rentalItem.getDiscount() > 0 ? newCharge.getTotalBeforeDiscounts() * rentalItem.getDiscount() / 100 : 0);
        logger.info("Discount percent: " + rentalItem.getDiscount());
        logger.info("Discount amount: " + newCharge.getTotalDiscount());

        newCharge.setFinalTotal(newCharge.getTotalBeforeDiscounts() - newCharge.getTotalDiscount());
        logger.info("Discount amount: " + newCharge.getFinalTotal());

        rentalItem.setCharge(newCharge);
    }

    public static boolean isWeekend(LocalDate date) {
        return date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY;
    }

    public static boolean isHoliday(LocalDate date) {
        List<LocalDate> holidays = HolidayCalculator.getHolidaysForYear(date.getYear());
        return holidays.stream()
                .anyMatch(holiday -> holiday.isEqual(date));
    }

}
