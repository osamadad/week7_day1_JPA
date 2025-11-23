package com.tuwaiq.jpa_e_commerce.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Merchant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "Sorry, the merchant name can't be empty, please try again")
    @Size(min = 3, max = 25, message = "Sorry, the merchant name can't be less than 3 or longer than 25 characters long, please try again")
    @Column(columnDefinition = "varchar(25) not null")
    private String name;
}
