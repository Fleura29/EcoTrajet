package com.mif10_g14_2024.mif10_g14.entity;

import com.mif10_g14_2024.mif10_g14.enums.UserType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String login;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserType type;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Itinerary> itineraries;

    public User(String login, String password, UserType type) {
        this.login = login;
        this.password = password;
        this.type = type;
        this.itineraries = new ArrayList<>(); // Initialisation à une liste vide
    }
}