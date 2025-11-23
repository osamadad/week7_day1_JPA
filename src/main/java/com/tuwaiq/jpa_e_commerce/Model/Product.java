package com.tuwaiq.jpa_e_commerce.Model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {
    @NotEmpty(message = "Sorry, the product id can't be empty, please try again")
    private String id;
    @NotEmpty(message = "Sorry, the product name can't be empty, please try again")
    @Size(min = 3,message = "Sorry, the product name can't be less than 3 characters long, please try again")
    private String name;
    @NotNull(message = "Sorry, the product price can't be empty, please try again")
    @Positive(message = "Sorry, the product price can't be 0 or less, please try again")
    private double price;
    @NotEmpty(message = "Sorry, the category id can't be empty, please try again")
    private String categoryId;
}
