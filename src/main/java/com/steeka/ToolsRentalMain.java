package com.steeka;


public class ToolsRentalMain {

    //TODO refactor to properties file
    public static final String toolsFile = "tools.txt";
    public static final String toolsChargesFile = "toolscharges.txt";

    public static void main(String[] args) {

        //1. Load Tools
        //1.a create Tools Repository
        //2. Load Tool Charges
        //2. a
        //3. Cache Holidays
        //4. Checkout
        //5. Generate Rental Agreement

        // refactor: file names could be loaded from a properties file
        // further down the road should be loaded from cache..

        //Load Tools first
        // Load Tool Charges second. There is a dependency due to tool code vs tool name
        ToolsFileLoader tfr = new ToolsFileLoader();
        tfr.loadFromFile(toolsFile);

        ToolsChargesFileLoader tcfl = new ToolsChargesFileLoader();
        tcfl.loadFromFile(toolsChargesFile);
        //TODO store toolCode in toolCharges. In general think about the data structure here as these are
        // 2 parallel structures with additional data
        /*
                                   String toolCode = ToolsRegistry.getToolCodeByName();
                            charges.setToolCode(toolCode);
         */

        ToolsRegistry.print();
        ToolsChargeRegistry.print();

        //ToolCharge tc = new ToolCharge.ToolChargeBuilder(dailyCharge).setHasWeekendCharge(true).setHolidayCharge(false)

        //generate rental agreement.


    }

}
