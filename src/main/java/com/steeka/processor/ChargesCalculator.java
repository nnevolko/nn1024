package com.steeka.processor;

import com.steeka.exception.ToolsRentalException;
import com.steeka.model.Charge;
import com.steeka.model.RentalItem;
import com.steeka.model.Tool;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
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

    public static void calculate(@NotNull RentalItem rentalItem, @NotNull Tool tool) {

        if (rentalItem.getDaysRented() < 1) {
            throw new ToolsRentalException("Tools can be rented for 1 or more days. Please adjust selected number of days.");
        }
        if (rentalItem.getDiscount() < 0 || rentalItem.getDiscount() > 100) {
            throw new ToolsRentalException("Rental discount cannot be greater than 100%. Please check with the Clerk.");
        }
        Charge newCharge = new Charge();
        LocalDate startRentalDate = rentalItem.getCheckoutDate().plusDays(1);
        LocalDate returnDate = rentalItem.getCheckoutDate().plusDays(rentalItem.getDaysRented());

        logger.debug("Start rental charges day: " + returnDate);
        logger.debug("Return date: " + returnDate);

        for (LocalDate date = startRentalDate; !date.isAfter(returnDate); date = date.plusDays(1)) {
            //Ladder      $1.99   Yes     Yes     No
           // Chainsaw    $1.49   Yes     No      Yes
            //Jackhammer  $2.99   Yes     No      No
            //
            if (isHoliday(date) && tool.getToolCharge().isHasHolidayCharge()){
                newCharge.addOneToDaysCharged();
            } else if(isWeekend(date) && tool.getToolCharge().isHasWeekendCharge()){
                newCharge.addOneToDaysCharged();
            } else if(!(isHoliday(date) || isWeekend(date)) && tool.getToolCharge().isHasWeekdayCharge()){
                newCharge.addOneToDaysCharged();
            }
        }

        logger.info("Total days charged: " + newCharge.getDaysCharged());

        newCharge.setTotalBeforeDiscounts(tool.getToolCharge().getDailyCharge().multiply(BigDecimal.valueOf(newCharge.getDaysCharged())));
        logger.info("Pre-discount charge: " + newCharge.getTotalBeforeDiscounts());

        BigDecimal discountedAmmount = rentalItem.getDiscount() > 0
                            ? newCharge.getTotalBeforeDiscounts()
                                        .multiply(BigDecimal.valueOf(rentalItem.getDiscount()).divide(BigDecimal.valueOf(100)))
                            : BigDecimal.ZERO;

        newCharge.setDiscountedAmount(discountedAmmount);

        newCharge.setFinalTotal(newCharge.getTotalBeforeDiscounts().subtract(newCharge.getDiscountedAmount()));
        logger.info("Discount amount: " + newCharge.getFinalTotal());

        logger.info("Total charge is: "+ newCharge);

        rentalItem.setCharge(newCharge);
        logger.info("Rental item: "+ rentalItem);
    }

    public static boolean isWeekend(@NotNull LocalDate date) {
        return date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY;
    }

    public static boolean isHoliday(@NotNull LocalDate date) {
        List<LocalDate> holidays = HolidayCalculator.getHolidaysForYear(date.getYear());
        return holidays.stream()
                .anyMatch(holiday -> holiday.isEqual(date));
    }

}
