package com.tuwaiq.jpa_e_commerce.Model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MerchantStock {
    @NotEmpty(message = "Sorry, the merchant stock id can't be empty, please try again")
    private String id;
    @NotEmpty(message = "Sorry, the product id can't be empty, please try again")
    private String productId;
    @NotEmpty(message = "Sorry, the merchant id can't be empty, please try again")
    private String merchantId;
    @NotNull(message = "Sorry, the stock can't be empty, please try again")
    @Min(value = 10, message = "Sorry, the stock can't be less than 10, please try again")
    private int stock;
}
