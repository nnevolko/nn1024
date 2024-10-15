package com.steeka.io;

import com.steeka.model.ToolCharge;
import com.steeka.model.ToolsChargeRegistry;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

//TODO add exception handling for input data
public class ToolsChargesFileLoader implements FileLoader<ToolsChargeRegistry> {

    private final String YES = "Yes";
    private final String NO = "No";
    private final String DOLLAR_SIGN ="$"; //TODO customize for locale

    @Override
    public ToolsChargeRegistry processInputLines(@NotNull List<String> inputLines) {
        ToolsChargeRegistry toolsChargeRegistry = new ToolsChargeRegistry();
        inputLines.stream()
                .skip(1)
                .forEach(
                        line -> {
                            String[] toolString = line.split("\\s+");
                            ToolCharge charges = createToolChargeFromFileDataRow(toolString);
                            toolsChargeRegistry.register(charges);
                        }
                );

        return toolsChargeRegistry;
    }

    public ToolCharge createToolChargeFromFileDataRow(@NotNull String[] dataRow) {

        ToolCharge charges = new ToolCharge();
        charges.setToolType(dataRow[0].trim());
        //use currency formatter to load amount wiht the currency indicator
        System.out.println("Value is: " + dataRow[1].trim().substring(1));

        charges.setDailyCharge(new BigDecimal(dataRow[1].trim().replace(DOLLAR_SIGN, "")));
        boolean hasWeekdayCharge = dataRow[2].trim().equalsIgnoreCase(YES);
        boolean hasWeekendCharge = dataRow[3].trim().equalsIgnoreCase(YES);
        boolean hasHolidayCharge = dataRow[4].trim().equalsIgnoreCase(YES);
        charges.setHasWeekdayCharge(hasWeekdayCharge);
        charges.setHasWeekendCharge(hasWeekendCharge);
        charges.setHasHolidayCharge(hasHolidayCharge);

        return charges;
    }
}
