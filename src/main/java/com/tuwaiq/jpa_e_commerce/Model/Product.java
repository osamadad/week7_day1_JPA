package com.tuwaiq.jpa_e_commerce.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "Sorry, the product name can't be empty, please try again")
    @Size(min = 3, max = 30,message = "Sorry, the product name can't be less than 3 or longer than 30 characters long, please try again")
    @Column(columnDefinition = "varchar(30) not null")
    private String name;
    @NotNull(message = "Sorry, the product price can't be empty, please try again")
    @Positive(message = "Sorry, the product price can't be 0 or less, please try again")
    @Column(columnDefinition = "int not null")
    private Double price;
    @NotEmpty(message = "Sorry, the category id can't be empty, please try again")
    @Size(max = 10,message = "Sorry, the category id can't be more than 10 characters")
    @Column(columnDefinition = "varchar(10) not null unique")
    private Integer categoryId;
}
