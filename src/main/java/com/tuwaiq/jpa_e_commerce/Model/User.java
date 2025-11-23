package com.tuwaiq.jpa_e_commerce.Model;

import Api.ApiResponse;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    @NotEmpty(message = "Sorry, your id can't be empty, please try again")
    private String id;
    @NotEmpty(message = "Sorry, your username can't be empty, please try again")
    @Size(min = 5, message = "Sorry, your username can't be less than 5 characters long, please try again")
    private String username;
    @NotEmpty(message = "Sorry, your password can't be empty, please try again")
    @Size(min = 6, message = "Sorry, your password can't be less than 6 characters long, please try again")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]+$",  message = "Sorry, your password can only contain alphabetic and numbers, please try again")
    private String password;
    @NotEmpty(message = "Sorry, your E-mail can't be empty, please try again")
    @Email(message = "Sorry, your E-mail must be in a valid E-mail format, please try again")
    private String email;
    @NotEmpty(message = "Sorry, your role can't be empty, please try again")
    @Pattern(regexp = "Admin|Customer",  message = "Sorry, your role can only be 'Admin' or 'Customer', please try again")
    private String role;
    @NotNull(message = "Sorry, your balance can't be empty, please try again")
    @Positive(message = "Sorry, your balance can't be less than 0, please try again")
    private double balance;
}
