package com.steeka;

import org.junit.jupiter.api.Test;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class RentalAgreementTest {

    @Test
    public void generateRentalAggreementTest(){


    }

   @Test
   public void formatDateTest(){
       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yy");
       LocalDate date = LocalDate.of(2024, 10, 10);
       Optional<String> result = Optional.ofNullable(RentalAgreement.formatDate(date, formatter));
       assertTrue(result.isPresent());
       assertEquals("10/10/2024", result.get());
   }

    @Test
    public void formatCurrencyTest(){
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);
        double amount = 9999.99;
        Optional<String> result = Optional.ofNullable(RentalAgreement.formatCurrency(amount, currencyFormatter));
        assertTrue(result.isPresent());
        assertEquals("$9,999.99", result.get());
    }
}