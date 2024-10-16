package com.steeka;

import com.steeka.model.RentalItem;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class RentalItemTest {

    @Test
    void getCheckoutDateTest() {

        //"9/3/15"
        RentalItem ri = new RentalItem("JAKR", "9/3/15", "5", "20%");
        LocalDate date =  ri.getCheckoutDate();
        //uuuu-MM-dd
        assertEquals(date.toString(), "2015-09-03");

    }
}