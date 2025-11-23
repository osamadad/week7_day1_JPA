package com.tuwaiq.jpa_e_commerce.Repository;

import com.tuwaiq.jpa_e_commerce.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {
}
