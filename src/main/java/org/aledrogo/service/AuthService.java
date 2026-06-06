package org.aledrogo.service;

import org.aledrogo.entity.User;
import org.aledrogo.repository.UserRepository;

public class AuthService {
    public final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(User user) throws Exception {
        User existingUser = userRepository.getByEmail(user.getEmail());
        if (existingUser  != null) {
            throw new Exception("Podany adres email jest w użyciu");
        }

        User createdUser = this.userRepository.create(user);
        return createdUser;
    }

    public void resetPassword(String email, String newPassword) throws Exception
    {
        User existingUser = userRepository.getByEmail(email);
        if (existingUser == null) {
            throw new Exception("Użytkownik o podanym adresie email nie istnieje");
        }

        existingUser.setPassword(newPassword);
        this.userRepository.update(existingUser);
    }

    public User login(String email, String password) throws Exception {
        User existingUser = userRepository.getByEmail(email);
        if (existingUser == null) {
            throw new Exception("Niepoprawny email lub hasło");
        }

        if (!existingUser.getPassword().equals(password)) {
            throw new Exception("Niepoprawny email lub hasło");
        }

        return existingUser;
    }
}
