package org.aledrogo.entity;

import java.math.BigDecimal;

public class Seller extends Buyer {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private BigDecimal balance = BigDecimal.valueOf(0);


    public Seller(String email, String password, String firstName, String lastName, String phoneNumber) {
        super(email, password, SystemRole.SELLER);

        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

}
