package org.aledrogo.entity;



public abstract class User {
    private String email;
    private String password;
    private final SystemRole role;

    public User(String email, String password, SystemRole role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public SystemRole getRole() {
        return this.role;
    }
}
