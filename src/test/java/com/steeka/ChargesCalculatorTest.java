package com.steeka;

import com.steeka.exception.ToolsRentalArgumentException;
import com.steeka.model.*;
import com.steeka.processor.ChargesCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ChargesCalculatorTest {

    ToolsRegistry toolsRegistry = new ToolsRegistry();
    ToolsChargeRegistry toolsChargeRegistry = new ToolsChargeRegistry();

    @BeforeEach
    void setUp() {
        Map<String, Tool> tools = Map.of(
                "CHNS", new Tool("CHNS", "Chainsaw", "Stihl"),
                "LADW", new Tool("LADW", "Ladder", "Werner"),
                "JAKD", new Tool("JAKD", "Jackhammer", "DeWalt"),
                "JAKR", new Tool("JAKR", "Jackhammer", "Ridgid"));
        tools.entrySet().stream().forEach(
                toolEntry -> toolsRegistry.register(toolEntry.getValue())
        );

        Map<String, ToolCharge> toolDailyAndExtraCharges = Map.of(
                "Ladder", new ToolCharge("Ladder", new BigDecimal("1.99"), true, true, false),
                "Chainsaw", new ToolCharge("Chainsaw", new BigDecimal("1.49"), true, false, true),
                "Jackhammer", new ToolCharge("Jackhammer", new BigDecimal("2.99"), true, false, false)
        );
        toolDailyAndExtraCharges.entrySet().stream().forEach(mapEntry ->
                toolsChargeRegistry.register(mapEntry.getValue()));

        toolsRegistry.getToolsRegistry().forEach((typeCode, toolToUpdate) -> {
            ToolCharge charge = toolsChargeRegistry.get(toolToUpdate.getType());
            if (charge != null) {
                toolToUpdate.setToolCharge(charge);
            }
        });
        toolsRegistry.getToolsRegistry().entrySet().stream()
                .forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue()));


    }

    @Test
    public void calculateTest() {

        /*
        Test 1 Test 2 Test 3 Test 4 Test 5 Test 6
        Tool code       JAKR    LADW    CHNS    JAKD    JAKR    JAKR
        Checkout date   9/3/15  7/2/20   7/2/15 9/3/15   7/2/15  7/2/20
        Rental days     5       3       5       6       9       4
        Discount        101%    10%     25%     0%      0%      50%*/

        // Test 1 ----------------------------------
        Exception exception1 = assertThrows(ToolsRentalArgumentException.class, () -> {
            RentalItem rentedItem1 = new RentalItem("JAKR", "9/3/15", "5", "101%");
            Tool jackhammer1 = toolsRegistry.get(rentedItem1.getToolCode());
            ChargesCalculator.calculate(rentedItem1, jackhammer1);
        });
        assertEquals("Rental discount cannot be greater than 100%. Please check with the Clerk.", exception1.getMessage());

        // Test 2 ----------------------------------
        RentalItem rentedItem2 = new RentalItem("LADW", "7/2/20", "3", "10%");
        Tool ladder1 = toolsRegistry.get(rentedItem2.getToolCode());
        ChargesCalculator.calculate(rentedItem2, ladder1);
        assertNotNull(ladder1.getToolCharge());

        assertTrue(ladder1.getToolCharge().getDailyCharge().compareTo(new BigDecimal("1.99")) == 0);
        assertTrue(ladder1.getToolCharge().isHasWeekdayCharge());
        assertTrue(ladder1.getToolCharge().isHasWeekendCharge());
        assertTrue(rentedItem2.getCharge().getTotalBeforeDiscounts().compareTo(new BigDecimal("3.98")) == 0);

        assertTrue(new BigDecimal("0.398").compareTo(rentedItem2.getCharge().getDiscountedAmount()) == 0); //0.597)
        assertEquals(rentedItem2.getCharge().getDaysCharged(), 2);
        assertTrue(new BigDecimal("3.582").compareTo(rentedItem2.getCharge().getFinalTotal()) == 0);

        // Test 3 ----------------------------------
        RentalItem rentedItem3 = new RentalItem("CHNS", "7/2/15", "5", "25%");
        Tool chainsaw1 = toolsRegistry.get(rentedItem3.getToolCode());
        ChargesCalculator.calculate(rentedItem3, chainsaw1);
        assertNotNull(chainsaw1.getToolCharge());

        assertTrue(chainsaw1.getToolCharge().getDailyCharge().compareTo(new BigDecimal("1.49")) == 0);
        assertTrue(chainsaw1.getToolCharge().isHasWeekdayCharge());
        assertFalse(chainsaw1.getToolCharge().isHasWeekendCharge());

        // Test 4 ----------------------------------
        RentalItem rentedItem4 = new RentalItem("JAKD", "9/3/15", "6", "0%");
        Tool jackhammer2 = toolsRegistry.get(rentedItem4.getToolCode());
        ChargesCalculator.calculate(rentedItem4, jackhammer2);
        assertNotNull(jackhammer2.getToolCharge());

        assertTrue(jackhammer2.getToolCharge().getDailyCharge().compareTo(new BigDecimal("2.99")) == 0);
        assertTrue(jackhammer2.getToolCharge().isHasWeekdayCharge());
        assertFalse(jackhammer2.getToolCharge().isHasWeekendCharge());

        // Test 5 ----------------------------------
        RentalItem rentedItem5 = new RentalItem("JAKD", "7/2/15", "9", "0%");
        Tool jackhammer3 = toolsRegistry.get(rentedItem5.getToolCode());
        ChargesCalculator.calculate(rentedItem5, jackhammer3);
        assertNotNull(jackhammer3.getToolCharge());

        assertTrue(jackhammer3.getToolCharge().getDailyCharge().compareTo(new BigDecimal("2.99")) == 0);
        assertTrue(jackhammer3.getToolCharge().isHasWeekdayCharge());
        assertFalse(jackhammer3.getToolCharge().isHasWeekendCharge());

        // Test 6 ----------------------------------
        RentalItem rentedItem6 = new RentalItem("JAKD", "7/2/20", "4", "50%");
        Tool jackhammer4 = toolsRegistry.get(rentedItem6.getToolCode());
        ChargesCalculator.calculate(rentedItem6, jackhammer4);
        assertNotNull(jackhammer4.getToolCharge());
        //7/2/20 is Thursday                                    ->7/2 start charging from next day
        //7/3 is a Holiday, 7/4 is observed on Friday           ->7/3 is a Holiday
        //7/4 Holiday an                                        ->7/4 is Weekend
        //7/5 is Sunday                                         -> 7/5 is Weekend
        //Return date 7/6/2020 Monday. There should only be one day that the customer is chared for.
        assertTrue(rentedItem6.getCharge().getTotalBeforeDiscounts().compareTo(new BigDecimal("2.99")) == 0);

        assertTrue(new BigDecimal("1.495").compareTo(rentedItem6.getCharge().getDiscountedAmount()) == 0); //0.597)
        assertEquals(rentedItem6.getCharge().getDaysCharged(), 1);
        assertTrue(new BigDecimal("1.495").compareTo(rentedItem6.getCharge().getFinalTotal()) == 0);

        assertTrue(jackhammer4.getToolCharge().getDailyCharge().compareTo(new BigDecimal("2.99")) == 0);
        assertTrue(jackhammer4.getToolCharge().isHasWeekdayCharge());
        assertFalse(jackhammer4.getToolCharge().isHasWeekendCharge());

    }

    @Test
    public void checkExceptions() {

        // Test 1 ----------------------------------
        Exception exception1 = assertThrows(ToolsRentalArgumentException.class, () -> {
            RentalItem rentedItem1 = new RentalItem("JAKR", "9/3/15", "0", "20%");
            Tool jackhammer1 = toolsRegistry.get(rentedItem1.getToolCode());
            ChargesCalculator.calculate(rentedItem1, jackhammer1);
        });
        assertEquals("Tools can be rented for 1 or more days. Please adjust selected number of days.", exception1.getMessage());

        // Test 2 ----------------------------------
        Exception exception2 = assertThrows(ToolsRentalArgumentException.class, () -> {
            RentalItem rentedItem2 = new RentalItem("JAKR", "9/3/15", "3", "101%");
            Tool jackhammer2 = toolsRegistry.get(rentedItem2.getToolCode());
            ChargesCalculator.calculate(rentedItem2, jackhammer2);
        });
        assertEquals("Rental discount cannot be greater than 100%. Please check with the Clerk.", exception2.getMessage());
    }

    @Test
    void isWeekendTest() {

        assertTrue(ChargesCalculator.isWeekend(LocalDate.of(2024, 10, 13)));
        assertFalse(ChargesCalculator.isWeekend(LocalDate.of(2024, 10, 15)));
        assertTrue(ChargesCalculator.isWeekend(LocalDate.of(2020, 7, 4)));
        assertTrue(ChargesCalculator.isWeekend(LocalDate.of(2020, 7, 5)));
    }

    @Test
    void isHolidayTest() {
        //Labord day 2024, 2nd of September
        LocalDate date = LocalDate.of(2024, 9, 2);
        assertTrue(ChargesCalculator.isHoliday(date));

        LocalDate date2 = LocalDate.of(2024, 10, 15);
        assertFalse(ChargesCalculator.isHoliday(date2));

        LocalDate date3 = LocalDate.of(2020, 07, 03);
        assertTrue(ChargesCalculator.isHoliday(date3));

    }
}