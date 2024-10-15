package com.steeka;

import com.steeka.exception.ToolsRentalArgumentException;
import com.steeka.model.*;
import com.steeka.processor.ChargesCalculator;
import com.steeka.service.Checkout;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class RentalAgreementTest {

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
    public void buildItemReceiptTest() {
        /*              Test 1     Test 2   Test 3  Test 4  Test 5  Test 6
        Tool code       JAKR        LADW    CHNS    JAKD    JAKR    JAKR
        Checkout date   9/3/15      7/2/20 7/2/15    9/3/15 7/2/15 7/2/20
        Rental days     5           3       5         6     9       4
        Discount        101%        10%     25%       0%    0%      50%
        */

        // Test 1 ----------------------------------
        Exception exception1 = assertThrows(ToolsRentalArgumentException.class, () -> {
            RentalItem rentedItem1= new RentalItem("JAKR", "9/3/15", "5", "101%");
            Tool jackhammer1 = toolsRegistry.get(rentedItem1.getToolCode());
            ChargesCalculator.calculate(rentedItem1, jackhammer1);
        });
        assertEquals("Rental discount cannot be greater than 100%. Please check with the Clerk.", exception1.getMessage());

        // Test 2 ----------------------------------

        Customer customer1 = new Customer("Nika Ne");
        RentalCart shoppingCart1 = new RentalCart();
        RentalItem rentedItem2 = new RentalItem("LADW", "7/2/20", "3", "10%");
        shoppingCart1.add(rentedItem2);
        Checkout newCheckout = new Checkout(customer1, shoppingCart1);
        newCheckout.processCheckOut(toolsRegistry);
        RentalAgreement agreement2 = newCheckout.generateRentalAgreement();
        agreement2.printRentalAgreement(toolsRegistry);
        StringBuilder receipt2 = agreement2.buildItemReceipt(shoppingCart1.getRentedTools().get(0), toolsRegistry);
        System.out.println(receipt2);
        assertNotNull(receipt2);
        //how to test receipt!!! have to refactor to Data Modal RentalAgreement
        //assertEquals(receipt2);
        /*

        assertTrue(ladder1.getToolCharge().getDailyCharge().compareTo(new BigDecimal("1.99")) == 0);
        assertTrue(ladder1.getToolCharge().isHasWeekdayCharge());
        assertTrue(ladder1.getToolCharge().isHasWeekendCharge());
        assertTrue(rentedItem2.getCharge().getTotalBeforeDiscounts().compareTo(new BigDecimal("5.97")) == 0);

        assertTrue(new BigDecimal("0.597").compareTo(rentedItem2.getCharge().getDiscountedAmount()) == 0); //0.597)
        assertEquals (rentedItem2.getCharge().getDaysCharged(), 3);
        assertTrue(new BigDecimal("5.373").compareTo(rentedItem2.getCharge().getFinalTotal()) == 0);
*/

    }

    @Test
    public void formatDateTest() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yy");

        LocalDate date = LocalDate.of(2024, 10, 10);
        Optional<String> result = Optional.ofNullable(RentalAgreement.formatDate(date, formatter));
        assertTrue(result.isPresent());
        assertEquals("10/10/24", result.get());

        LocalDate date2 = LocalDate.of(2024, 9, 7);
        Optional<String> result2 = Optional.ofNullable(RentalAgreement.formatDate(date2, formatter));
        assertTrue(result.isPresent());
        assertEquals("09/07/24", result2.get());
    }

    @Test
    public void formatCurrencyTest() {
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);
        BigDecimal amount = new BigDecimal("9999.99");
        Optional<String> result = Optional.ofNullable(RentalAgreement.formatCurrency(amount, currencyFormatter));
        assertTrue(result.isPresent());
        assertEquals("$9,999.99", result.get());
    }
}