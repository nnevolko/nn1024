package com.steeka.service;

import com.steeka.model.*;
import com.steeka.processor.ChargesCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Checkout {

    private static final Logger log = LoggerFactory.getLogger(Checkout.class);
    private Customer customer;
    private RentalCart rentalCart;
    private ToolsRegistry toolsRegistry;

    public Checkout(Customer customer, RentalCart cart, ToolsRegistry toolsRegistry) {
        this.customer = customer;
        this.rentalCart = cart;
        this.toolsRegistry = toolsRegistry;
    }

    public void processCheckOut() {
        //TODO this would process a list of rental items if needed in the future
        RentalItem rentalItem = this.rentalCart.getRentalItem();
        ChargesCalculator.calculate(rentalItem, toolsRegistry.get(rentalItem.getToolCode()));
    }

    public RentalAgreementDTO generateRentalAgreement() {
        RentalAgreementDTO agreementDTO = RentalAgreement.generateRentalAgreement(rentalCart.getRentalItem(), toolsRegistry);
        return agreementDTO;
    }
}
