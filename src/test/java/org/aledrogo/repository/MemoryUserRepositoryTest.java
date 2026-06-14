package org.aledrogo.repository;

import org.aledrogo.entity.Customer;
import org.aledrogo.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemoryUserRepositoryTest {

    private MemoryUserRepository repository;

    @BeforeEach
    void setUp() {
        repository = new MemoryUserRepository();
    }

    @Test
    void getByEmailReturnsMatchingUser() {
        User user = repository.create(new Customer("a@example.com", "password"));

        assertSame(user, repository.getByEmail("a@example.com"));
        assertNull(repository.getByEmail("missing@example.com"));
    }

    @Test
    void updateReplacesMatchingUserAndKeepsOthers() {
        User a = repository.create(new Customer("a@example.com", "password"));
        User b = repository.create(new Customer("b@example.com", "password"));

        a.setEmail("a-new@example.com");
        repository.update(a);

        assertEquals(2, repository.getAll().size(), "update must not change the number of users");
        assertEquals("a-new@example.com", repository.getById(a.getId()).getEmail());
        assertSame(b, repository.getById(b.getId()), "the other user must be left intact");
    }

    @Test
    void deleteRemovesOnlyTheTargetUser() {
        User a = repository.create(new Customer("a@example.com", "password"));
        User b = repository.create(new Customer("b@example.com", "password"));

        repository.delete(a);

        assertNull(repository.getById(a.getId()), "deleted user must be gone");
        assertSame(b, repository.getById(b.getId()), "the other user must remain");
        assertEquals(1, repository.getAll().size());
    }
}
