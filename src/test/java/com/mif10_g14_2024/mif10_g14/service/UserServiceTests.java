package com.mif10_g14_2024.mif10_g14.service;

import com.mif10_g14_2024.mif10_g14.dao.UserRepository;
import com.mif10_g14_2024.mif10_g14.entity.User;
import com.mif10_g14_2024.mif10_g14.enums.UserType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest(classes = {UserService.class})
public class UserServiceTests {
    @Autowired
    UserService userService;

    @MockitoBean
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        Mockito.when(userRepository.save(any(User.class)))
                .thenAnswer(invocation -> {
                    User user = invocation.getArgument(0, User.class);
                    return new User(user.getLogin(), user.getPassword(), user.getType());
                });

        Mockito.when(userRepository.findById(1L))
                .thenReturn(Optional.of(new User("toto", "TOTO", UserType.UTILISATEUR)));
        Mockito.when(userRepository.findById(2L))
                .thenReturn(Optional.empty());

        Mockito.when(userRepository.findByLogin("toto"))
                .thenReturn(Optional.of(new User("toto", "TOTO", UserType.UTILISATEUR)));
        Mockito.when(userRepository.findByLogin("tata"))
                .thenReturn(Optional.empty());
    }

    @Test
    void testCreateUser() {
        // Given
        User test1 = new User("titi", "TITI", UserType.ADMIN);

        // When
        User res1 = userService.createUser(test1);

        // Then
        assertEquals(res1, test1);
    }

    @Test
    void testGetUserById() {
        // When
        Optional<User> u1 = userService.getUserById(1L);
        Optional<User> u2 = userService.getUserById(2L);

        // Then
        assertEquals(new User("toto", "TOTO", UserType.UTILISATEUR), u1.get());
        assertTrue(u2.isEmpty());
    }

    @Test
    void testGetUserByLogin() {
        // When
        Optional<User> u1 = userService.getUserByLogin("toto");
        Optional<User> u2 = userService.getUserByLogin("titi");

        // Then
        assertEquals(new User("toto", "TOTO", UserType.UTILISATEUR), u1.get());
        assertTrue(u2.isEmpty());
    }
}
