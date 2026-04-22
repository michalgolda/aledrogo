package org.aledrogo.entity;

public class Customer extends Buyer {
    public Customer(String email, String password) {
        super(email, password, SystemRole.CUSTOMER);
    }
}
