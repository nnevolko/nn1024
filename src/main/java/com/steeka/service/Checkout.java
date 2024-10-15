package com.steeka.service;

import com.steeka.model.Customer;
import com.steeka.model.RentalAgreement;
import com.steeka.model.RentalCart;
import com.steeka.model.ToolsRegistry;
import com.steeka.processor.ChargesCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Checkout {

    private static final Logger log = LoggerFactory.getLogger(Checkout.class);
    private Customer customer;
    private RentalCart cart;

    public Checkout(Customer customer, RentalCart cart) {
        this.customer = customer;
        this.cart = cart;
    }

    public void processCheckOut(ToolsRegistry toolsRegistry) {
        cart.getRentedTools().stream().forEach(rentedItem -> {
                    //TODO should double check that this tool exists, it can also exist but have 0 availability
                    ChargesCalculator.calculate(rentedItem, toolsRegistry.get(rentedItem.getToolCode()));
                }
        );
    }

    public RentalAgreement generateRentalAgreement() {
        return new RentalAgreement(cart.getRentedTools());
    }
}
