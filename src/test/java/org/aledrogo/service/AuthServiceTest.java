package org.aledrogo.service;

import org.aledrogo.entity.Customer;
import org.aledrogo.entity.SystemRole;
import org.aledrogo.entity.User;
import org.aledrogo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthService authService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new Customer("jan@example.com", "haslo123");
    }

    @Test
    void registerReturnsCreatedUserWhenEmailDoesNotExist() throws Exception {
        when(userRepository.getByEmail(testUser.getEmail())).thenReturn(null);
        when(userRepository.create(testUser)).thenReturn(testUser);

        User result = authService.register(testUser);

        assertNotNull(result);
        assertEquals(testUser.getEmail(), result.getEmail());
        verify(userRepository).create(testUser);
    }

    @Test
    void registerThrowsWhenEmailIsAlreadyInUse() {
        when(userRepository.getByEmail(testUser.getEmail())).thenReturn(testUser);

        Exception ex = assertThrows(Exception.class, () -> authService.register(testUser));

        assertEquals("Podany adres email jest w użyciu", ex.getMessage());
        verify(userRepository, never()).create(any());
    }

    @Test
    void registerNeverCallsCreateWhenEmailAlreadyExists() {
        when(userRepository.getByEmail(testUser.getEmail())).thenReturn(testUser);

        assertThrows(Exception.class, () -> authService.register(testUser));

        verify(userRepository, never()).create(any(User.class));
    }

    @Test
    void resetPasswordUpdatesPasswordWhenUserExists() throws Exception {
        when(userRepository.getByEmail(testUser.getEmail())).thenReturn(testUser);

        authService.resetPassword(testUser.getEmail(), "newPassword456");

        assertEquals("newPassword456", testUser.getPassword());
        verify(userRepository).update(testUser);
    }

    @Test
    void resetPasswordThrowsWhenUserDoesNotExist() {
        when(userRepository.getByEmail("missing@example.com")).thenReturn(null);

        Exception ex = assertThrows(Exception.class,
                () -> authService.resetPassword("missing@example.com", "newPassword"));

        assertEquals("Użytkownik o podanym adresie email nie istnieje", ex.getMessage());
        verify(userRepository, never()).update(any());
    }

    @Test
    void resetPasswordCallsUpdateExactlyOnceWhenUserExists() throws Exception {
        when(userRepository.getByEmail(testUser.getEmail())).thenReturn(testUser);

        authService.resetPassword(testUser.getEmail(), "newPassword");

        verify(userRepository, times(1)).update(testUser);
    }

    @Test
    void loginReturnsUserWhenCredentialsAreValid() throws Exception {
        when(userRepository.getByEmail(testUser.getEmail())).thenReturn(testUser);

        User result = authService.login(testUser.getEmail(), testUser.getPassword());

        assertNotNull(result);
        assertEquals(testUser.getEmail(), result.getEmail());
    }

    @Test
    void loginThrowsWhenEmailDoesNotExist() {
        when(userRepository.getByEmail("missing@example.com")).thenReturn(null);

        Exception ex = assertThrows(Exception.class,
                () -> authService.login("missing@example.com", "password"));

        assertEquals("Niepoprawny email lub hasło", ex.getMessage());
    }

    @Test
    void loginThrowsWhenPasswordIsIncorrect() {
        when(userRepository.getByEmail(testUser.getEmail())).thenReturn(testUser);

        Exception ex = assertThrows(Exception.class,
                () -> authService.login(testUser.getEmail(), "wrongPassword"));

        assertEquals("Niepoprawny email lub hasło", ex.getMessage());
    }

    @Test
    void loginReturnsTheSameErrorMessageForWrongEmailAndWrongPassword() {
        when(userRepository.getByEmail("missing@example.com")).thenReturn(null);
        Exception exMissingEmail = assertThrows(Exception.class,
                () -> authService.login("missing@example.com", "anything"));

        when(userRepository.getByEmail(testUser.getEmail())).thenReturn(testUser);
        Exception exWrongPassword = assertThrows(Exception.class,
                () -> authService.login(testUser.getEmail(), "wrongPassword"));

        assertEquals(exMissingEmail.getMessage(), exWrongPassword.getMessage());
    }
}