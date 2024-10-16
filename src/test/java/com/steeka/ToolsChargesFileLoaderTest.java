package com.steeka;

import com.steeka.io.ToolsChargesFileLoader;
import com.steeka.model.ToolCharge;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ToolsChargesFileLoaderTest {

    @Test
    public void createToolChargeFromFileDataRowTest(){


        ToolsChargesFileLoader tcfl = new ToolsChargesFileLoader();

        String[] chargesRowData1 = {"Ladder", "$1.99", "Yes", "Yes", "No"};
        ToolCharge charge1 = tcfl.createToolChargeFromFileDataRow(chargesRowData1);
        assertEquals("Ladder", charge1.getToolType());

        String[] chargesRowData2 = {"Chainsaw", "$1.49", "Yes", "No", "Yes"};
        ToolCharge charge2 = tcfl.createToolChargeFromFileDataRow(chargesRowData2);
        assertEquals(true, charge2.isHasWeekdayCharge());
        assertEquals(false, charge2.isHasWeekendCharge());
        assertEquals(true, charge2.isHasHolidayCharge());

        String[] chargesRowData3 = {"Jackhammer", "$2.99", "Yes", "No", "No"};
        ToolCharge charge3 = tcfl.createToolChargeFromFileDataRow(chargesRowData3);
        assertTrue(new BigDecimal("2.99").compareTo(charge3.getDailyCharge())==0);

    }
}