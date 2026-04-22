package org.aledrogo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GreetingServiceTest {
    private final GreetingService service = new GreetingService();

    @Test
    void shouldReturnGreetingMessage() {
        String message = service.execute("World");
        assertEquals("Hello, World!", message);
    }
}