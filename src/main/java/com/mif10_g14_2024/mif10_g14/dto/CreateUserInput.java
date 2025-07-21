package com.mif10_g14_2024.mif10_g14.dto;

import com.mif10_g14_2024.mif10_g14.enums.UserType;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserInput {
    private String login;
    private String password;
    private UserType type;
}