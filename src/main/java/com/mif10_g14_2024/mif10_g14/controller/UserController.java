package com.mif10_g14_2024.mif10_g14.controller;

import com.mif10_g14_2024.mif10_g14.dto.CreateUserInput;
import com.mif10_g14_2024.mif10_g14.dto.LoginDto;
import com.mif10_g14_2024.mif10_g14.entity.User;
import com.mif10_g14_2024.mif10_g14.service.UserService;
import com.mif10_g14_2024.mif10_g14.service.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;


@Controller
public class UserController {
    @Autowired
    UserService userService;

    @MutationMapping
    public User createUser(@Argument CreateUserInput input) {
        User newUser = new User();
        newUser.setLogin(input.getLogin());
        newUser.setPassword(input.getPassword());
        newUser.setType(input.getType());
        return userService.createUser(newUser);
    }

    @MutationMapping
    public String login(@Argument LoginDto input) {
        User user = userService.getUserByLogin(input.getLogin())
                .orElseThrow(() -> new RuntimeException("Invalid login or password"));

        if (!user.getPassword().equals(input.getPassword())) {
            throw new RuntimeException("Invalid login or password");
        }

        // Generate a JWT token
        return JwtUtil.generateToken(user.getLogin());
    }

    @QueryMapping
    public User getUserById(@Argument Long id, HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Authorization header is missing or invalid");
        }

        String token = authorizationHeader.substring(7); // Remove "Bearer " prefix

        if (!JwtUtil.validateToken(token)) {
            throw new RuntimeException("Invalid or expired token");
        }

        return userService.getUserById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    /*@QueryMapping
    public User getUserWithHistoriques(@Argument Long id) {
        User user = userService.getUserById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        List<TrajetHistorique> historiques = trajetHistoriqueService.getHistoriquesByUserId(id);
        user.setHistoriques(historiques); // Ajoute les historiques à l'utilisateur

        return user;
    }*/

}