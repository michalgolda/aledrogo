package org.aledrogo.repository;

import org.aledrogo.entity.User;

import java.util.ArrayList;

public class MemoryUserRepository extends UserRepository {
    private ArrayList<User> users = new ArrayList<>();

    @Override
    public User getByEmail(String email) {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }

        return null;
    }

    @Override
    public User getById(int id) {
        for (User user : users) {
            if (user.getId().equals(id)) {
                return user;
            }
        }

        return null;
    }

    @Override
    public ArrayList<User> getAll() {
        return users;
    }

    @Override
    public User create(User entity) {
        users.add(entity);
        return entity;
    }

    @Override
    public User update(User entity) {
        ArrayList<User> usersForReplace = new ArrayList<>();
        for (User user : users) {
            if (user.getId().equals(entity.getId())) {
                usersForReplace.add(entity);
            } else {
                usersForReplace.add(user);
            }
        }
        users = usersForReplace;
        return entity;
    }

    @Override
    public void delete(User entity) {
        ArrayList<User> usersForReplace = new ArrayList<>();
        for (User user : users) {
            if (!user.getId().equals(entity.getId())) {
                usersForReplace.add(user);
            }
        }
        users = usersForReplace;
    }
}
