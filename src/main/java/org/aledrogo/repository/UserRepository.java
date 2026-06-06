package org.aledrogo.repository;

import org.aledrogo.entity.User;

public abstract class UserRepository extends Repository<User> {
    public abstract User getByEmail(String email);
}
