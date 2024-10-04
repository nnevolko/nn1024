package com.steeka;

import java.util.Arrays;
import java.util.List;

//TODO add exception handling for input data
public class ToolsChargesFileLoader implements FileLoader {

    private final String YES = "Yes";
    private final String NO = "No";

    public void processInputLines(List<String> inputLines) {
        inputLines.stream()
                .skip(1)
                .forEach(
                        line -> {
                            String[] toolString = line.split("\\s+");
                            ToolCharge charges = createToolChargeFromFileDataRow(toolString);
                            ToolsChargeRegistry.register(charges);
                        }
                );


    }

    public ToolCharge createToolChargeFromFileDataRow(String[] dataRow){
        // System.out.println(Arrays.toString(toolString));

        ToolCharge charges = new ToolCharge();
        charges.setToolName(dataRow[0].trim());
        //skip dollar sign, get substrig
        charges.setDailyCharge(Double.parseDouble(dataRow[1].trim().substring(1)));
        boolean hasWeekdayCharge = dataRow[2].trim().equalsIgnoreCase(YES);
        boolean hasWeekendCharge = dataRow[3].trim().equalsIgnoreCase(YES);
        boolean hasHolidayCharge = dataRow[4].trim().equalsIgnoreCase(YES);
        charges.setHasWeekdayCharge(hasWeekdayCharge);
        charges.setHasWeekendCharge(hasWeekendCharge);
        charges.setHasHolidayCharge(hasHolidayCharge);

        return charges;
    }
}
