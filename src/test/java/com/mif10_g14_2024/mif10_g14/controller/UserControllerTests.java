package com.mif10_g14_2024.mif10_g14.controller;

import com.mif10_g14_2024.mif10_g14.dto.CreateUserInput;
import com.mif10_g14_2024.mif10_g14.dto.LoginDto;
import com.mif10_g14_2024.mif10_g14.entity.User;
import com.mif10_g14_2024.mif10_g14.enums.UserType;
import com.mif10_g14_2024.mif10_g14.service.UserService;
import com.mif10_g14_2024.mif10_g14.service.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {UserController.class})
public class UserControllerTests {
    @Autowired
    UserController userController;

    @MockitoBean
    UserService userService;

    User testUser = new User("toto", "TOTO", UserType.UTILISATEUR);

    @BeforeEach
    void setUp() {
        Mockito.when(userService.createUser(Mockito.any(User.class)))
                .thenAnswer(invocation -> {
                    User user = invocation.getArgument(0);
                    if (user.getLogin().equals(testUser.getLogin()) &&
                            user.getPassword().equals(testUser.getPassword()) &&
                            user.getType().equals(testUser.getType())) {
                        return testUser;
                    }
                    return null;
                });

        Mockito.when(userService.getUserById(1L)).thenReturn(Optional.of(testUser));

        Mockito.when(userService.getUserById(2L)).thenReturn(Optional.empty());
    }

    @Test
    public void testCreateUser() {
        // Given
        CreateUserInput input = new CreateUserInput(
                "toto",
                "TOTO",
                UserType.UTILISATEUR
        );

        // When
        User user = userController.createUser(input);

        // Then
        assertEquals(testUser, user);
    }

    @Test
    public void testLogin() {
        // Given
        LoginDto loginDto1 = new LoginDto("toto", "TOTO");
        LoginDto loginDto2 = new LoginDto("toto", "TITI");
        LoginDto loginDto3 = new LoginDto("tata", "TOTO");

        Mockito.when(userService.getUserByLogin("toto")).thenReturn(Optional.ofNullable(testUser));
        Mockito.when(userService.getUserByLogin("tata")).thenReturn(Optional.empty());

        // Then
        assertDoesNotThrow(() -> userController.login(loginDto1));

        assertThrows(RuntimeException.class, () -> userController.login(loginDto2));
        assertThrows(RuntimeException.class, () -> userController.login(loginDto3));
    }

    @Test
    public void testGetUserById() {
        // Genere le token pour test
        String validToken = JwtUtil.generateToken(testUser.getLogin());

        // Mock HttpServletRequest avec Auth header
        HttpServletRequest mockRequest = Mockito.mock(HttpServletRequest.class);
        Mockito.when(mockRequest.getHeader("Authorization")).thenReturn("Bearer " + validToken);

        // Test avec token valid
        User user = assertDoesNotThrow(() -> userController.getUserById(1L, mockRequest));
        assertEquals(testUser, user);

        // Test avec token invalid
        Mockito.when(mockRequest.getHeader("Authorization")).thenReturn("Bearer invalid_token");
        assertThrows(RuntimeException.class, () -> userController.getUserById(1L, mockRequest));

        // Test sans toekn
        Mockito.when(mockRequest.getHeader("Authorization")).thenReturn(null);
        assertThrows(RuntimeException.class, () -> userController.getUserById(1L, mockRequest));
    }
}
