package com.tuwaiq.jpa_e_commerce.Model;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MerchantStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "Sorry, the product id can't be empty, please try again")
    @Size(max = 10,message = "Sorry, the product id can't be more than 10 characters")
    @Column(columnDefinition = "varchar(10) not null unique")
    private Integer productId;
    @NotEmpty(message = "Sorry, the merchant id can't be empty, please try again")
    @Size(max = 10,message = "Sorry, the merchant id can't be more than 10 characters")
    @Column(columnDefinition = "varchar(10) not null unique")
    private Integer merchantId;
    @NotNull(message = "Sorry, the stock can't be empty, please try again")
    @Min(value = 10, message = "Sorry, the stock can't be less than 10, please try again")
    @Column(columnDefinition = "int not null")
    private Integer stock;
}
