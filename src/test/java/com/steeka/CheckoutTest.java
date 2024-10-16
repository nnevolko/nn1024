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
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CheckoutTest {

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

        Customer customer2 = new Customer("Nika Ne");

        RentalItem rentedItem2 = new RentalItem("LADW", "7/2/20", "3", "10%");
        RentalCart shoppingCart2 = new RentalCart(rentedItem2);

        Checkout newCheckout = new Checkout(customer2, shoppingCart2, toolsRegistry);
        newCheckout.processCheckOut();
        RentalAgreementDTO agreementDTO2 = newCheckout.generateRentalAgreement();
        assertNotNull(agreementDTO2);
        agreementDTO2.printRentalAgreement();

        assertEquals("LADW", agreementDTO2.getToolCode());
        assertEquals("Ladder", agreementDTO2.getType());
        assertEquals("Werner", agreementDTO2.getBrand());
        assertTrue(new BigDecimal("1.99").compareTo(agreementDTO2.getDailyCharge()) == 0);
        assertTrue(LocalDate.of(2020,7,2).equals(agreementDTO2.getCheckoutDate()));
        assertTrue(LocalDate.of(2020, 7, 5).equals(agreementDTO2.getReturnDate()));
        assertEquals(10, agreementDTO2.getDiscount());
        assertEquals(3, agreementDTO2.getDaysRented());

        assertTrue(new BigDecimal("5.97").compareTo(agreementDTO2.getTotalBeforeDiscounts()) == 0);
        assertTrue(new BigDecimal("0.597").compareTo(agreementDTO2.getDiscountedAmount()) == 0); //0.597)

        assertTrue(new BigDecimal("5.373").compareTo(agreementDTO2.getFinalTotal()) == 0);
        assertEquals (agreementDTO2.getDaysCharged(), 3);


        // Test 3 ----------------------------------
        Customer customer3 = new Customer("Customer Three");

        RentalItem rentedItem3 = new RentalItem("CHNS", "7/2/15", "5", "25%");
        RentalCart shoppingCart3 = new RentalCart(rentedItem3);

        Checkout newCheckout3 = new Checkout(customer3, shoppingCart3, toolsRegistry);
        newCheckout3.processCheckOut();
        RentalAgreementDTO agreementDTO3 = newCheckout3.generateRentalAgreement();
        assertNotNull(agreementDTO3);
        agreementDTO3.printRentalAgreement();

        assertEquals("CHNS", agreementDTO3.getToolCode());
        assertEquals("Chainsaw", agreementDTO3.getType());
        assertEquals("Stihl", agreementDTO3.getBrand());
        assertTrue(new BigDecimal("1.49").compareTo(agreementDTO3.getDailyCharge()) == 0);
        assertTrue(LocalDate.of(2015,7,2).equals(agreementDTO3.getCheckoutDate()));
        assertTrue(LocalDate.of(2015, 7, 7).equals(agreementDTO3.getReturnDate()));
        assertEquals(25, agreementDTO3.getDiscount());
        assertEquals(5, agreementDTO3.getDaysRented());

        assertTrue(new BigDecimal("7.45").compareTo(agreementDTO3.getTotalBeforeDiscounts()) == 0);
        assertTrue(new BigDecimal("1.8625").compareTo(agreementDTO3.getDiscountedAmount()) == 0); //0.597)

        assertTrue(new BigDecimal("5.5875").compareTo(agreementDTO3.getFinalTotal()) == 0);
        assertEquals (agreementDTO3.getDaysCharged(), 5);

        // Test 4 ----------------------------------
        Customer customer4 = new Customer("Customer Four");

        RentalItem rentedItem4 = new RentalItem("JAKD", "9/3/15", "6", "0%");
        RentalCart shoppingCart4 = new RentalCart(rentedItem4);

        Checkout newCheckout4 = new Checkout(customer4, shoppingCart4, toolsRegistry);
        newCheckout4.processCheckOut();
        RentalAgreementDTO agreementDTO4 = newCheckout4.generateRentalAgreement();
        assertNotNull(agreementDTO4);
        agreementDTO4.printRentalAgreement();

        assertEquals("JAKD", agreementDTO4.getToolCode());
        assertEquals("Jackhammer", agreementDTO4.getType());
        assertEquals("DeWalt", agreementDTO4.getBrand());
        assertTrue(new BigDecimal("2.99").compareTo(agreementDTO4.getDailyCharge()) == 0);
        assertTrue(LocalDate.of(2015,9,3).equals(agreementDTO4.getCheckoutDate()));
        assertTrue(LocalDate.of(2015, 9, 9).equals(agreementDTO4.getReturnDate()));
        assertEquals(0, agreementDTO4.getDiscount());
        assertEquals(6, agreementDTO4.getDaysRented());

        assertTrue(new BigDecimal("17.94").compareTo(agreementDTO4.getTotalBeforeDiscounts()) == 0);
        assertTrue(new BigDecimal("0").compareTo(agreementDTO4.getDiscountedAmount()) == 0); //0.597)

        assertTrue(new BigDecimal("17.94").compareTo(agreementDTO4.getFinalTotal()) == 0);
        assertEquals (agreementDTO4.getDaysCharged(), 6);

        // Test 5 ----------------------------------
        Customer customer5 = new Customer("Customer Five");

        RentalItem rentedItem5 = new RentalItem("JAKR", "7/2/15", "9", "0%");
        RentalCart shoppingCart5 = new RentalCart(rentedItem5);

        Checkout newCheckout5 = new Checkout(customer5, shoppingCart5, toolsRegistry);
        newCheckout5.processCheckOut();
        RentalAgreementDTO agreementDTO5 = newCheckout5.generateRentalAgreement();
        assertNotNull(agreementDTO5);
        agreementDTO5.printRentalAgreement();

        assertEquals("JAKR", agreementDTO5.getToolCode());
        assertEquals("Jackhammer", agreementDTO5.getType());
        assertEquals("Ridgid", agreementDTO5.getBrand());
        assertTrue(new BigDecimal("2.99").compareTo(agreementDTO5.getDailyCharge()) == 0);
        assertTrue(LocalDate.of(2015,7,2).equals(agreementDTO5.getCheckoutDate()));
        assertTrue(LocalDate.of(2015, 7, 11).equals(agreementDTO5.getReturnDate()));
        assertEquals(0, agreementDTO5.getDiscount());
        assertEquals(9, agreementDTO5.getDaysRented());

        assertTrue(new BigDecimal("26.91").compareTo(agreementDTO5.getTotalBeforeDiscounts()) == 0);
        assertTrue(new BigDecimal("0").compareTo(agreementDTO5.getDiscountedAmount()) == 0); //0.597)

        assertTrue(new BigDecimal("26.91").compareTo(agreementDTO5.getFinalTotal()) == 0);
        assertEquals (agreementDTO5.getDaysCharged(), 9);



                /*     Test 1     Test 2   Test 3  Test 4  Test 5  Test 6
        Tool code       JAKR        LADW    CHNS    JAKD    JAKR    JAKR
        Checkout date   9/3/15      7/2/20 7/2/15    9/3/15 7/2/15 7/2/20
        Rental days     5           3       5         6     9       4
        Discount        101%        10%     25%       0%    0%      50%
        */
        // Test 6 ----------------------------------
        Customer customer6 = new Customer("Customer Six");

        RentalItem rentedItem6 = new RentalItem("JAKR", "7/2/20", "4", "50%");
        RentalCart shoppingCart6 = new RentalCart(rentedItem6);

        Checkout newCheckout6 = new Checkout(customer6, shoppingCart6, toolsRegistry);
        newCheckout6.processCheckOut();
        RentalAgreementDTO agreementDTO6 = newCheckout6.generateRentalAgreement();
        assertNotNull(agreementDTO6);
        agreementDTO6.printRentalAgreement();

        assertEquals("JAKR", agreementDTO6.getToolCode());
        assertEquals("Jackhammer", agreementDTO6.getType());
        assertEquals("Ridgid", agreementDTO6.getBrand());
        assertTrue(new BigDecimal("2.99").compareTo(agreementDTO6.getDailyCharge()) == 0);
        assertTrue(LocalDate.of(2020,7,2).equals(agreementDTO6.getCheckoutDate()));
        assertTrue(LocalDate.of(2020, 7, 6).equals(agreementDTO6.getReturnDate()));
        assertEquals(50, agreementDTO6.getDiscount());
        assertEquals(4, agreementDTO6.getDaysRented());

        assertTrue(new BigDecimal("11.96").compareTo(agreementDTO6.getTotalBeforeDiscounts()) == 0);
        assertTrue(new BigDecimal("5.980").compareTo(agreementDTO6.getDiscountedAmount()) == 0); //0.597)

        assertTrue(new BigDecimal("5.980").compareTo(agreementDTO6.getFinalTotal()) == 0);
        assertEquals (agreementDTO6.getDaysCharged(), 4);

    }

    @Test
    public void formatDateTest() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yy");

        LocalDate date = LocalDate.of(2024, 10, 10);
        Optional<String> result = Optional.ofNullable(RentalAgreementDTO.formatDate(date, formatter));
        assertTrue(result.isPresent());
        assertEquals("10/10/24", result.get());

        LocalDate date2 = LocalDate.of(2024, 9, 7);
        Optional<String> result2 = Optional.ofNullable(RentalAgreementDTO.formatDate(date2, formatter));
        assertTrue(result.isPresent());
        assertEquals("09/07/24", result2.get());
    }

    @Test
    public void formatCurrencyTest() {
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);
        BigDecimal amount = new BigDecimal("9999.99");
        Optional<String> result = Optional.ofNullable(RentalAgreementDTO.formatCurrency(amount, currencyFormatter));
        assertTrue(result.isPresent());
        assertEquals("$9,999.99", result.get());
    }
}