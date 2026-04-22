package org.aledrogo.entity;

public abstract class Buyer extends User {
    public Buyer(String email, String password, SystemRole role) {
        super(email, password, role);
    }
}
