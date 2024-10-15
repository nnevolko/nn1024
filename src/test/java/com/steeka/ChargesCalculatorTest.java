package com.steeka;

import com.steeka.exception.ToolsRentalArgumentException;
import com.steeka.exception.ToolsRentalException;
import com.steeka.model.*;
import com.steeka.processor.ChargesCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
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
                    "JAKD",new Tool("JAKD", "Jackhammer", "DeWalt"),
                    "JAKR", new Tool("JAKR", "Jackhammer", "Ridgid"));
            tools.entrySet().stream().forEach(
                    toolEntry -> toolsRegistry.register(toolEntry.getValue())
            );

            Map<String, ToolCharge> toolDailyAndExtraCharges = Map.of(
                    "Ladder", new ToolCharge("Ladder", 1.99, true, true, false),
                    "Chainsaw", new ToolCharge("Chainsaw", 1.49, true, false, true),
                    "Jackhammer", new ToolCharge("Jackhammer", 2.99, true, false, false)
            );
            toolDailyAndExtraCharges.entrySet().stream().forEach(mapEntry ->
                    toolsChargeRegistry.register(mapEntry.getValue()));

        //associate tool daily charges with the tool
        /*
            tools.forEach((typeCode, toolToUpdate) ->{
                toolDailyAndExtraCharges.entrySet().stream()
                        .filter(chargeEntry -> chargeEntry.getValue().getToolType().equals(toolToUpdate.getType()))
                        .findFirst()
                        .ifPresent(chargeEntry -> toolToUpdate.setToolCharge(chargeEntry.getValue()));
            });
*/

        toolsRegistry.getToolsRegistry().forEach((typeCode, toolToUpdate) ->{
            ToolCharge charge = toolsChargeRegistry.get(toolToUpdate.getType());
            if(charge!=null){
                toolToUpdate.setToolCharge(charge);
            }
        });
        toolsRegistry.getToolsRegistry().entrySet().stream()
                .forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue()));


    }

    @Test
    public void calculateTest(){

        /*
        Test 1 Test 2 Test 3 Test 4 Test 5 Test 6
        Tool code       JAKR    LADW    CHNS    JAKD    JAKR    JAKR
        Checkout date   9/3/15  7/2/20   7/2/15 9/3/15   7/2/15  7/2/20
        Rental days     5       3       5       6       9       4
        Discount        101%    10%     25%     0%      0%      50%*/

        // Test 1 ----------------------------------
        Exception exception1 = assertThrows(ToolsRentalArgumentException.class, () -> {
            RentalItem rentedItem1= new RentalItem("JAKR", "9/3/15", "5", "101%");
            Tool jackhammer1 = toolsRegistry.get(rentedItem1.getToolCode());
            ChargesCalculator.calculate(rentedItem1, jackhammer1);
        });
        assertEquals("Rental discount cannot be greater than 100%. Please check with the Clerk.", exception1.getMessage());

        // Test 2 ----------------------------------
        RentalItem rentedItem2 = new RentalItem("LADW", "7/2/20", "3", "10%");
        Tool ladder1 = toolsRegistry.get(rentedItem2.getToolCode());
        ChargesCalculator.calculate(rentedItem2, ladder1);
        assertNotNull(ladder1.getToolCharge());

        assertTrue(Double.compare(ladder1.getToolCharge().getDailyCharge(), 1.99) == 0);
        assertTrue(ladder1.getToolCharge().isHasWeekdayCharge());
        assertTrue(ladder1.getToolCharge().isHasWeekendCharge());
        assertTrue(Double.compare(rentedItem2.getCharge().getTotalBeforeDiscounts(), 5.97) == 0);
        assertTrue(Double.compare(rentedItem2.getCharge().getTotalDiscount(), 0.597) == 0);
        assertEquals (rentedItem2.getCharge().getDaysCharged(), 3);

        double expected = 5.37299;
        double delta = 0.00001;
        assertEquals(expected, rentedItem2.getCharge().getFinalTotal(), delta);

        // Test 3 ----------------------------------
        RentalItem rentedItem3 = new RentalItem("CHNS", "7/2/15", "5", "25%");
        Tool chainsaw1 = toolsRegistry.get(rentedItem3.getToolCode());
        ChargesCalculator.calculate(rentedItem3, chainsaw1);
        assertNotNull(chainsaw1.getToolCharge());

        assertTrue(Double.compare(chainsaw1.getToolCharge().getDailyCharge(), 1.49) == 0);
        assertTrue(chainsaw1.getToolCharge().isHasWeekdayCharge());
        assertFalse(chainsaw1.getToolCharge().isHasWeekendCharge());

        // Test 4 ----------------------------------
        RentalItem rentedItem4 = new RentalItem("JAKD", "9/3/15", "6", "0%");
        Tool jackhammer2 = toolsRegistry.get(rentedItem4.getToolCode());
        ChargesCalculator.calculate(rentedItem4, jackhammer2);
        assertNotNull(jackhammer2.getToolCharge());

        assertTrue(Double.compare(jackhammer2.getToolCharge().getDailyCharge(), 2.99) == 0);
        assertTrue(jackhammer2.getToolCharge().isHasWeekdayCharge());
        assertFalse(jackhammer2.getToolCharge().isHasWeekendCharge());

        // Test 5 ----------------------------------
        RentalItem rentedItem5 = new RentalItem("JAKD", "7/2/15", "9", "0%");
        Tool jackhammer3 = toolsRegistry.get(rentedItem5.getToolCode());
        ChargesCalculator.calculate(rentedItem5, jackhammer3);
        assertNotNull(jackhammer3.getToolCharge());

        assertTrue(Double.compare(jackhammer3.getToolCharge().getDailyCharge(), 2.99) == 0);
        assertTrue(jackhammer3.getToolCharge().isHasWeekdayCharge());
        assertFalse(jackhammer3.getToolCharge().isHasWeekendCharge());

        // Test 6 ----------------------------------
        RentalItem rentedItem6 = new RentalItem("JAKD", "7/2/20", "4", "50%");
        Tool jackhammer4 = toolsRegistry.get(rentedItem6.getToolCode());
        ChargesCalculator.calculate(rentedItem6, jackhammer4);
        assertNotNull(jackhammer4.getToolCharge());

        assertTrue(Double.compare(jackhammer4.getToolCharge().getDailyCharge(), 2.99) == 0);
        assertTrue(jackhammer4.getToolCharge().isHasWeekdayCharge());
        assertFalse(jackhammer4.getToolCharge().isHasWeekendCharge());

    }

    @Test
    public void checkExceptions(){

        // Test 1 ----------------------------------
        Exception exception1 = assertThrows(ToolsRentalArgumentException.class, () -> {
            RentalItem rentedItem1= new RentalItem("JAKR", "9/3/15", "0", "20%");
            Tool jackhammer1 = toolsRegistry.get(rentedItem1.getToolCode());
            ChargesCalculator.calculate(rentedItem1, jackhammer1);
        });
        assertEquals("Tools can be rented for 1 or more days. Please adjust selected number of days.", exception1.getMessage());

        // Test 2 ----------------------------------
        Exception exception2 = assertThrows(ToolsRentalArgumentException.class, () -> {
            RentalItem rentedItem2= new RentalItem("JAKR", "9/3/15", "3", "101%");
            Tool jackhammer2 = toolsRegistry.get(rentedItem2.getToolCode());
            ChargesCalculator.calculate(rentedItem2, jackhammer2);
        });
        assertEquals("Rental discount cannot be greater than 100%. Please check with the Clerk.", exception2.getMessage());
    }

    @Test
    void isWeekendTest() {

        assertTrue(ChargesCalculator.isWeekend(LocalDate.of(2024, 10, 13)));
        assertFalse(ChargesCalculator.isWeekend(LocalDate.of(2024, 10, 15)));
    }

    @Test
    void isHolidayTest() {
        //Labord day 2024, 2nd of September
        LocalDate date = LocalDate.of(2024, 9, 2);
        assertTrue(ChargesCalculator.isHoliday(date));

        LocalDate date2 = LocalDate.of(2024, 10, 15);
        assertFalse(ChargesCalculator.isHoliday(date2));

    }
}