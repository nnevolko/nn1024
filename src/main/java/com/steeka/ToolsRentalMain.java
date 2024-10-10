package com.steeka;

//TODO add logger
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ToolsRentalMain {

    //TODO refactor to properties file
    public static final String toolsFile = "tools.txt";
    public static final String toolsChargesFile = "toolscharges.txt";

    private static final Logger logger = LoggerFactory.getLogger(ToolsRentalMain.class);

    public static void main(String[] args) {

        logger.info("Application started.");
        logger.debug("This is a debug message.");
        logger.error("An error occurred.", new RuntimeException("Test exception"));


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

       // Test 1 Test 2 Test 3 Test 4 Test 5 Test 6
       // Tool code JAKR LADW CHNS JAKD JAKR JAKR
       // Checkout date 9/3/15 7/2/20 7/2/15 9/3/15 7/2/15 7/2/20
       // Rental days 5 3 5 6 9 4
       // Discount 101% 10% 25% 0% 0% 50%

        Customer customer1 = new Customer("Nika Nevolko");
        //RentalItem ri = new RentalItem("JAKR", "9/3/15", "5", "101%");
        RentalItem ri = new RentalItem("JAKR", "10/11/24", "5", "20%");
        RentalCart cart = new RentalCart();
        cart.add(ri);
        Checkout newCheckout = new Checkout(customer1, cart);
        newCheckout.processCheckOut();
        RentalAgreement agreement =newCheckout.generateRentalAgreement();
        agreement.printRentalAgreement();

    }

}
