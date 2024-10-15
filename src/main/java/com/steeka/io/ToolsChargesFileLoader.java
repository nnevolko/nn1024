package com.steeka.io;

import com.steeka.model.ToolCharge;
import com.steeka.model.ToolsChargeRegistry;

import java.util.List;

//TODO add exception handling for input data
public class ToolsChargesFileLoader implements FileLoader<ToolsChargeRegistry> {

    private final String YES = "Yes";
    private final String NO = "No";

    @Override
    public ToolsChargeRegistry processInputLines(List<String> inputLines) {
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

    public ToolCharge createToolChargeFromFileDataRow(String[] dataRow) {
        // System.out.println(Arrays.toString(toolString));

        ToolCharge charges = new ToolCharge();
        charges.setToolType(dataRow[0].trim());
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
