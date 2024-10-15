package com.steeka;


import com.steeka.model.*;
import com.steeka.service.Checkout;
import com.steeka.service.ToolsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ToolsRentalMain {

    private static final Logger logger = LoggerFactory.getLogger(ToolsRentalMain.class);

    public static void main(String[] args) {

        logger.info("Tools rental application started.");
        //TODO add  Lombok to generate @Data (getters, setters, hashCode, toString and etc)
        // resolve maven dependency issue

        ToolsService toolsService = new ToolsService();
        ToolsRegistry toolsRegistry = toolsService.getTools();

        logger.debug(toolsRegistry.toString());

        //1. Create Customer
        //2. Create Rental item/items
        //3. Create Shopping cart
        //4. add rental items to shopping cart
        //5. Create new Checkout Process
        //6.  process the rentals (given a number of days, calculate the charge,
        //          number of days charged, tackle free rentals and etc
        // 7. Create Rental Agreement for a checkout
        // 8. print this rental agreement

        Customer customer1 = new Customer("Nika Ne");
        RentalCart cart = populateSampleShoppingCart(customer1);
        Checkout newCheckout = new Checkout(customer1, cart);
        newCheckout.processCheckOut(toolsRegistry);
        RentalAgreement agreement = newCheckout.generateRentalAgreement();
        agreement.printRentalAgreement(toolsRegistry);

        logger.info("Exiting Tools rental application.");
    }

    public static RentalCart populateSampleShoppingCart(Customer customer) {
        RentalItem ri = new RentalItem("JAKR", "10/11/24", "5", "20%");
        RentalCart shoppingCart = new RentalCart();
        shoppingCart.add(ri);
        return shoppingCart;
    }

}
