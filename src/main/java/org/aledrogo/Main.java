package org.aledrogo;

import org.aledrogo.GreetingService;
import org.aledrogo.repository.MemoryUserRepository;
import org.aledrogo.repository.UserRepository;

public class Main {
    public final UserRepository userRepository = new MemoryUserRepository();

    public static void main(String[] args) {
        GreetingService greetingService = new GreetingService();
        String message = greetingService.execute("World");
        System.out.println(message);
    }
}