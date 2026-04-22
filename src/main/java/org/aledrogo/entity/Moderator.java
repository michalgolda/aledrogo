package org.aledrogo.entity;

public class Moderator extends Buyer {
    public Moderator(String email, String password, String firstName, String lastName) {
        super(email, password, SystemRole.MODERATOR);
    }
}
