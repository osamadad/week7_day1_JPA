package com.tuwaiq.jpa_e_commerce.Model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Merchant {
    @NotEmpty(message = "Sorry, the merchant id can't be empty, please try again")
    private String id;
    @NotEmpty(message = "Sorry, the merchant name can't be empty, please try again")
    @Size(min = 3, message = "Sorry, the merchant name can't be less than 3 characters long, please try again")
    private String name;
}
