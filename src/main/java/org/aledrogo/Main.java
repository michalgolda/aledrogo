package org.aledrogo;

import org.aledrogo.GreetingService;

public class Main {
    public static void main(String[] args) {
        GreetingService greetingService = new GreetingService();
        String message = greetingService.execute("World");
        System.out.println(message);
    }
}