package com.steeka;

public class Checkout {

    private Customer customer;
    private RentalCart cart;

    public Checkout(Customer customer, RentalCart cart) {
        this.customer = customer;
        this.cart = cart;
    }

    public void processCheckOut(){
       //
    }
    public RentalAgreement generateRentalAgreement(){

        return new RentalAgreement( cart.getRentedTools());
    }
}
