package com.tuwaiq.jpa_e_commerce.Model;

import Api.ApiResponse;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "Sorry, your username can't be empty, please try again")
    @Size(min = 5, max = 15, message = "Sorry, your username can't be less than 5 or longer than 15 characters long, please try again")
    @Column(columnDefinition = "varchar(15) not null")
    private String username;
    @NotEmpty(message = "Sorry, your password can't be empty, please try again")
    @Size(min = 6, max = 15, message = "Sorry, your password can't be less than 6 or longer than 15 characters long, please try again")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]+$",  message = "Sorry, your password can only contain alphabetic and numbers, please try again")
    @Column(columnDefinition = "varchar(15) not null")
    private String password;
    @NotEmpty(message = "Sorry, your E-mail can't be empty, please try again")
    @Email(message = "Sorry, your E-mail must be in a valid E-mail format, please try again")
    @Size(max = 35, message = "Sorry your E-mail can't be longer than 35 characters long, please try again")
    @Column(columnDefinition = "varchar(35) not null unique")
    private String email;
    @NotEmpty(message = "Sorry, your role can't be empty, please try again")
    @Pattern(regexp = "Admin|Customer",  message = "Sorry, your role can only be 'Admin' or 'Customer', please try again")
    @Column(columnDefinition = "varchar(10) not null")
    private String role;
    @NotNull(message = "Sorry, your balance can't be empty, please try again")
    @Positive(message = "Sorry, your balance can't be less than 0, please try again")
    @Column(columnDefinition = "int not null")
    private Double balance;
}
